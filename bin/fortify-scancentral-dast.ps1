#
# Example script to perform Fortify ScanCentral DAST scan
#

# Import some supporting functions
Import-Module $PSScriptRoot\modules\FortifyFunctions.psm1

# Import local environment specific settings
$EnvSettings = $(ConvertFrom-StringData -StringData (Get-Content ".\.env" | Where-Object {-not ($_.StartsWith('#'))} | Out-String))
$SSCUrl = $EnvSettings['SSC_URL']
$SSCUsername = $EnvSettings['SSC_USERNAME']
$SSCPassword = $EnvSettings['SSC_PASSWORD']
$ScanCentralDASTAPI = $EnvSettings['SCANCENTRAL_DAST_API']
$ScanCentralDASTCICD = $EnvSettings['SCANCENTRAL_DAST_CICD_TOKEN']

# Test we have Fortify installed successfully
Test-Environment
if ([string]::IsNullOrEmpty($SSCUrl)) { throw "SSC URL has not been set" }
if ([string]::IsNullOrEmpty($ScanCentralDASTAPI)) { throw "ScanCentral DAST API has not been set" }
if ([string]::IsNullOrEmpty($ScanCentralDASTCICD)) { throw "ScanCentral DAST CI CD Token has not been set" }
if ([string]::IsNullOrEmpty($SSCUsername)) { throw "SSC Username has not been set" }
if ([string]::IsNullOrEmpty($SSCPassword)) { throw "SSC Password has not been set" }

# run the scan

Write-Host Invoking ScanCentral DAST...
Write-Host "$PSScriptRoot\functions\ScanCentralDASTScan.ps1 -ApiUri $ScanCentralDASTAPI -Username $SSCUsername -Password $SSCPassword -CiCdToken $ScanCentralDASTCICD"
& $PSScriptRoot\functions\ScanCentralDASTScan.ps1 -ApiUri $ScanCentralDASTAPI -Username $SSCUsername -Password $SSCPassword -CiCdToken $ScanCentralDASTCICD

Write-Host Completed
Write-Host You can check status at: $SSCUrl
