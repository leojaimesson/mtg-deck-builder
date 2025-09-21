#!/bin/bash
set -e

# Configurations
SWAGGER_VERSION="5.0.1"
SWAGGER_UI_DIST_URL="https://github.com/swagger-api/swagger-ui/releases/download/v$SWAGGER_VERSION/swagger-ui-$SWAGGER_VERSION.zip"

echo "===== Generating Swagger UI (optimized) ====="

# Build the project
echo "[1/5] Building project..."
./gradlew build

# Generate OpenAPI JSON via Gradle plugin
echo "[2/5] Generating openapi.json..."
./gradlew generateOpenApiDocs

# Prepare docs folder
echo "[3/5] Preparing docs folder..."
mkdir -p docs

# The plugin 1.6.0 generates JSON in build/swagger by default
cp build/swagger/openapi.json docs/openapi.json

# Download Swagger UI dist
echo "[4/5] Downloading Swagger UI dist v$SWAGGER_VERSION..."
TMP_DIR=$(mktemp -d)
curl -L "$SWAGGER_UI_DIST_URL" -o "$TMP_DIR/swagger-ui.zip"
unzip -q "$TMP_DIR/swagger-ui.zip" -d "$TMP_DIR"
cp -r "$TMP_DIR/dist/"* docs/

# Create index.html and swagger-initializer.js
echo "[5/5] Creating index.html and swagger-initializer.js..."
cat > docs/index.html <<EOF
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>API Docs</title>
  <link rel="stylesheet" type="text/css" href="swagger-ui.css" >
</head>
<body>
  <div id="swagger-ui"></div>
  <script src="swagger-ui-bundle.js"></script>
  <script src="swagger-ui-standalone-preset.js"></script>
  <script src="swagger-initializer.js"></script>
</body>
</html>
EOF

cat > docs/swagger-initializer.js <<EOF
window.onload = function() {
  const ui = SwaggerUIBundle({
    url: "openapi.json",
    dom_id: '#swagger-ui',
    presets: [
      SwaggerUIBundle.presets.apis,
      SwaggerUIStandalonePreset
    ],
    layout: "StandaloneLayout"
  });
  window.ui = ui;
}
EOF

echo "Swagger UI generation completed! docs/ folder is ready."

# Clean up temporary files
rm -rf "$TMP_DIR"
