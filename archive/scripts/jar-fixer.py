import zipfile
import re
import os

## MODIFIES JAR FILES LOCATED AT ./libs, USED IN CONJUNCTION WITH Forgix 1.2.9
## You're probably reading this while the script is in archive/scripts. Move it to the build folder for merged files.

def fix_duplicate_prefix(text: str, prefix: str) -> str:
    return re.sub(f'(?:{re.escape(prefix)})+', prefix, text)

prefixes_to_fix = ["fabric.", "forge.", "neoforge."]
escaped_char = """
"""

def process_jar(input_path: str, output_path: str):
    with zipfile.ZipFile(input_path, 'r') as zin:
        with zipfile.ZipFile(output_path, 'w', compression=zipfile.ZIP_DEFLATED) as zout:
            for item in zin.infolist():
                original_filename = item.filename
                data = zin.read(item)

                if original_filename.startswith("META-INF/services/") and not original_filename.endswith("/"):
                    print(f"[{input_path}] Processing: {original_filename}".replace("META-INF/services/", ""))

                    # Fix filename
                    service_name = original_filename[len("META-INF/services/"):]
                    for prefix in prefixes_to_fix:
                        service_name = fix_duplicate_prefix(service_name, prefix)
                    new_filename = "META-INF/services/" + service_name

                    # Fix content
                    try:
                        text = data.decode('utf-8')
                        original_text = text
                        for prefix in prefixes_to_fix:
                            text = fix_duplicate_prefix(text, prefix)
                        data = text.encode('utf-8')
                        print(f"[{input_path}] file_name → {original_filename} → {new_filename}".replace("META-INF/services/", ""))
                        print(f"[{input_path}] file_text → {original_text} → {text}".replace(escaped_char, ""))
                        print()
                    except UnicodeDecodeError:
                        print(f"[{input_path}] ⚠ Failed to decode as Unicode UTF-8 text: {original_filename}")
                        new_filename = original_filename

                    zout.writestr(new_filename, data)
                else:
                    zout.writestr(original_filename, data)

    print(f"[{input_path}] finished! Output saved as: {output_path}")

libs_folder = "libs"

output_folder = os.path.join(".", "fixed")
os.makedirs(output_folder, exist_ok=True)

# Loop through all .jar files in the libs/ folder
for filename in os.listdir(libs_folder):
    if filename.endswith(".jar") and not filename.endswith("-fixed.jar"):
        input_path = os.path.join(libs_folder, filename)
        base, ext = os.path.splitext(filename)
        # output_path = os.path.join(output_folder, f"{base}-fixed{ext}")
        output_path = os.path.join(output_folder, f"{base}{ext}")
        process_jar(input_path, output_path)