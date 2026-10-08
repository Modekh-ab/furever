<# Builds every Stonecutter version and copies release JARs into dist/. #>
[CmdletBinding()]
param()

$ErrorActionPreference = "Stop"
$gradleWrapper = Join-Path $PSScriptRoot "gradlew.bat"
& $gradleWrapper buildAllVersions --console=plain
if ($LASTEXITCODE -ne 0) {
    throw "The multi-version Gradle build failed (exit code $LASTEXITCODE)."
}
Write-Host "All release JARs are in $(Join-Path $PSScriptRoot 'dist')" -ForegroundColor Green
