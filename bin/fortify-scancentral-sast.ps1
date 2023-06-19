#
# Example script to perform Fortify ScanCentral SAST scan
#

# Parameters
param (
    [Parameter(Mandatory=$false)]
    [switch]$QuickScan
)

# Import some supporting functions
Import-Module $PSScriptRoot\modules\FortifyFunctions.psm1 -Force

# Import local environment specific settings
$EnvSettings = $(ConvertFrom-StringData -StringData (Get-Content (Join-Path "." -ChildPath ".env") | Where-Object {-not ($_.StartsWith('#'))} | Out-String))
$AppName = $EnvSettings['SSC_APP_NAME']
$AppVersion = $EnvSettings['SSC_APP_VER_NAME']
$SSCAuthToken = $EnvSettings['SSC_AUTH_TOKEN'] # CIToken
$ScanCentralCtrlUrl = $EnvSettings['SCANCENTRAL_CTRL_URL']
$ScanCentralPoolId = $EnvSettings['SCANCENTRAL_POOL_ID'] # Not yet used
$ScanCentralEmail = $EnvSettings['SCANCENTRAL_EMAIL']

$ScanPolicy = "classic" # or "devops" or "security"
$ScanPrecision = 4
$ScanSwitches = "-Dcom.fortify.sca.Phase0HigherOrder.Languages=javascript,typescript -Dcom.fortify.sca.EnableDOMModeling=true -Dcom.fortify.sca.follow.imports=true -Dcom.fortify.sca.exclude.unimported.node.modules=true"
$BuildVersion = $(git log --format="%H" -n 1)
$BuildLabel = $(Get-Date -format "ddMyyyy-hh:mm.ss")
$FilterFile = Join-Path ".\etc" -ChildPath "sca-filter.txt"
$CustomRules = Join-Path ".\etc" -ChildPath "sca-custom-rules.xml"
if ($QuickScan) {
    $ScanPrecision = 1
    $ScanPolicy = "devops"
}
$PackageName = "Package.zip"

# Test we have Fortify installed successfully
Test-Environment
if (-not (Get-ScanCentralCientInstalled)) { throw "ScanCentral client executable cannot be found" }
if ([string]::IsNullOrEmpty($ScanCentralCtrlUrl)) { throw "ScanCentral Controller URL has not been set" }
if ([string]::IsNullOrEmpty($ScanCentralEmail)) { throw "ScanCentral Email has not been set" }
if ([string]::IsNullOrEmpty($SSCAuthToken)) { throw "SSC Authentication token has not been set" }
if ([string]::IsNullOrEmpty($AppName)) { throw "Application Name has not been set" }
if ([string]::IsNullOrEmpty($AppVersion)) { throw "Application Version has not been set" }

# Delete Package if it already exists
if (Test-Path $PackageName) {
   Remove-Item $PackageName -Verbose
}

# Package, upload and run the scan and import results into SSC
Write-Host Invoking ScanCentral SAST ...
& scancentral -url $ScanCentralCtrlUrl start -upload -uptoken $SSCAuthToken -sp $PackageName `
    -application $AppName -version $AppVersion -bt gradle -bf build.gradle -bc "clean build" `
    -fprssc "$($AppName).fpr" -filter $FilterFile -rules $CustomRules `
    -email $ScanCentralEmail -block -o -f "$($AppName).fpr" -sargs "-scan-precision $ScanPrecision" `
    -sargs "-build-project $AppName" -sargs "-build-version $BuildVersion" -sargs "-build-label $BuildLabel"
#    -sargs "-scan-policy $ScanPolicy" `

# Summarise issue count by analyzer
if (Get-SCAInstalled) {
    & fprutility -information -analyzerIssueCounts -project "$($AppName).fpr"
    Write-Host Generating PDF report...
    & ReportGenerator '-Dcom.fortify.sca.ProjectRoot=.fortify' -user "Demo User" -format pdf -f "$($AppName).pdf" -source "$($AppName).fpr"
}

# Uncomment if not using "-block" in scancentral command above
#Write-Host
#Write-Host You can check ongoing status with:
#Write-Host " scancentral -url $ScanCentralCtrlUrl status -token [received-token]"
