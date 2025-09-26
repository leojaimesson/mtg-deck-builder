#!/bin/bash
set -e

# Configurations
SWAGGER_VERSION="5.29.0"
SWAGGER_UI_DIST_URL="https://github.com/swagger-api/swagger-ui/archive/refs/tags/v$SWAGGER_VERSION.zip"


echo "===== Generating Swagger UI (optimized) ====="

# Build the project
echo "[1/5] Building project..."
./app/gradlew build

# Generate OpenAPI JSON via Gradle plugin
echo "[2/5] Generating openapi.json..."
./app/gradlew generateOpenApiDocs

# Prepare docs folder
echo "[3/5] Preparing docs folder..."
mkdir -p app/docs

# The plugin 1.6.0 generates JSON in build/swagger by default
cp app/build/swagger/openapi.json app/docs/openapi.json

# Download Swagger UI dist
echo "[4/5] Downloading Swagger UI dist v$SWAGGER_VERSION..."
TMP_DIR=$(mktemp -d)
curl -L "$SWAGGER_UI_DIST_URL" -o "$TMP_DIR/swagger-ui-$SWAGGER_VERSION.zip"
unzip -q "$TMP_DIR/swagger-ui-$SWAGGER_VERSION.zip" -d "$TMP_DIR"
cp -r "$TMP_DIR/swagger-ui-$SWAGGER_VERSION/dist/"* app/docs/

# Create index.html and swagger-initializer.js
echo "[5/5] Creating index.html and swagger-initializer.js..."
cat > docs/index.html <<EOF
<!-- HTML for static distribution bundle build -->
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8">
    <title>Swagger UI</title>
    <link rel="stylesheet" type="text/css" href="./swagger-ui.css" />
    <link rel="stylesheet" type="text/css" href="index.css" />
    <link rel="icon" type="image/png" href="./favicon-32x32.png" sizes="32x32" />
    <link rel="icon" type="image/png" href="./favicon-16x16.png" sizes="16x16" />
  </head>

  <body>
    <div id="swagger-ui"></div>
    <script src="./swagger-ui-bundle.js" charset="UTF-8"> </script>
    <script src="./swagger-ui-standalone-preset.js" charset="UTF-8"> </script>
    <script src="./swagger-initializer.js" charset="UTF-8"> </script>
  </body>
</html>
EOF

cat > app/docs/swagger-initializer.js <<EOF
window.onload = function() {
  window.ui = SwaggerUIBundle({
    url: "openapi.json",
    dom_id: '#swagger-ui',
    deepLinking: true,
    presets: [
      SwaggerUIBundle.presets.apis,
      SwaggerUIStandalonePreset
    ],
    plugins: [
      SwaggerUIBundle.plugins.DownloadUrl
    ],
    layout: "StandaloneLayout"
  });
};
EOF

echo "Swagger UI generation completed! app/docs/ folder is ready."

# Clean up temporary files
rm -rf "$TMP_DIR"
