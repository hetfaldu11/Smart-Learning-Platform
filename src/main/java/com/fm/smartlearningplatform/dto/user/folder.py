import os

def create_java_file(path: str, filename: str, package: str, class_type: str):
    file_path = os.path.join(path, filename)
    class_name = filename.replace(".java", "")

    content = f"""package {package};

public {class_type} {class_name} {{

}}
"""
    with open(file_path, "w") as f:
        f.write(content)
    print(f"   📄 {filename}")

def capitalize_name(name: str) -> str:
    return name.strip().capitalize()

def create_dto_structure(base_path: str, base_package: str, folders: list[str]):
    for folder in folders:
        name = capitalize_name(folder)

        request_path = os.path.join(base_path, folder, "request")
        response_path = os.path.join(base_path, folder, "response")

        os.makedirs(request_path, exist_ok=True)
        os.makedirs(response_path, exist_ok=True)

        request_package = f"{base_package}.{folder}.request"
        response_package = f"{base_package}.{folder}.response"

        print(f"\n✅ {folder}/")
        print(f"  request/")
        create_java_file(request_path, f"Create{name}Request.java", request_package, "class")
        create_java_file(request_path, f"Update{name}Request.java", request_package, "class")
        print(f"  response/")
        create_java_file(response_path, f"{name}Response.java", response_package, "class")

def main():
    base_path = input("Enter base path (e.g. src/main/java/com/fm/dto): ").strip()
    base_package = input("Enter base package (e.g. com.fm.smartlearningplatform.dto): ").strip()

    print("\nEnter folder names one by one. Type 'done' when finished:")
    folders = []
    while True:
        folder = input("Folder name: ").strip()
        if folder.lower() == "done":
            break
        if folder:
            folders.append(folder)

    if not folders:
        print("No folders entered.")
        return

    create_dto_structure(base_path, base_package, folders)
    print(f"\n🎉 Done! Created {len(folders)} folder(s) in '{base_path}'")

if __name__ == "__main__":
    main()