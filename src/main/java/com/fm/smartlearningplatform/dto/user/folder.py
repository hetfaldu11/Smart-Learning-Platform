from pathlib import Path

BASE_PATH = Path(
    ""
)

entities = [
    "Authority",
    "EducationLevel",
    "Gender",
    "Interest",
    "Language",
    "Platform",
    "Profession",
    "Role",
    "Skill",
    "Theme"
]

for entity in entities:
    package_name = entity[0].lower() + entity[1:]

    request_dir = BASE_PATH / package_name / "request"
    response_dir = BASE_PATH / package_name / "response"

    request_dir.mkdir(parents=True, exist_ok=True)
    response_dir.mkdir(parents=True, exist_ok=True)

    # Create Request
    create_request = f"""package com.fm.smartlearningplatform.dto.user.{package_name}.request;

import jakarta.validation.constraints.NotBlank;

public record Create{entity}Request(

        @NotBlank(message = "{entity} name is required")
        String name

) {{
}}
"""

    # Update Request
    update_request = f"""package com.fm.smartlearningplatform.dto.user.{package_name}.request;

import jakarta.validation.constraints.NotBlank;

public record Update{entity}Request(

        @NotBlank(message = "{entity} name is required")
        String name

) {{
}}
"""

    # Response
    response = f"""package com.fm.smartlearningplatform.dto.user.{package_name}.response;

public record {entity}Response(

        Long id,
        String name

) {{
}}
"""

    # Delete Response
    delete_response = f"""package com.fm.smartlearningplatform.dto.user.{package_name}.response;

public record Delete{entity}Response(

        String message

) {{
}}
"""

    (request_dir / f"Create{entity}Request.java").write_text(create_request)
    (request_dir / f"Update{entity}Request.java").write_text(update_request)

    (response_dir / f"{entity}Response.java").write_text(response)
    (response_dir / f"Delete{entity}Response.java").write_text(delete_response)

print("DTO generation completed successfully.")

other_entities = [
    "User"
    "UserDevice",
    "UserInterest",
    "UserPreference",
    "UserProfile",
    "UserRole",
    "UserSkill",
    "UserSocialLink",
    "UserVerification",
    "RoleAuthority"
]

for entity in other_entities:
    package_name = entity[0].lower() + entity[1:]

    request_dir = BASE_PATH / package_name / "request"
    response_dir = BASE_PATH / package_name / "response"

    request_dir.mkdir(parents=True, exist_ok=True)
    response_dir.mkdir(parents=True, exist_ok=True)

    create_request = f"""package com.fm.smartlearningplatform.dto.user.{package_name}.request;

public record Create{entity}Request() {{
}}
"""

    update_request = f"""package com.fm.smartlearningplatform.dto.user.{package_name}.request;

public record Update{entity}Request() {{
}}
"""

    response = f"""package com.fm.smartlearningplatform.dto.user.{package_name}.response;

public record {entity}Response() {{
}}
"""

    delete_response = f"""package com.fm.smartlearningplatform.dto.user.{package_name}.response;

public record Delete{entity}Response() {{
}}
"""

    (request_dir / f"Create{entity}Request.java").write_text(create_request)
    (request_dir / f"Update{entity}Request.java").write_text(update_request)
    (response_dir / f"{entity}Response.java").write_text(response)
    (response_dir / f"Delete{entity}Response.java").write_text(delete_response)