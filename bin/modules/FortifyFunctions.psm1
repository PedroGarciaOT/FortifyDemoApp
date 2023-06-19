$Script:SCALocalInstall = $True
$Script:ScanCentralClient = $True

function Test-Environment {

    $WarningPreference = "Continue"

    Write-Host "Validating Fortify Installation..."

    # Check Source Analyzer is on the path
    if ((Get-Command "sourceanalyzer.exe" -ErrorAction SilentlyContinue) -eq $null)
    {
        Write-Warning "Unable to find sourceanalyzer.exe in your PATH - local analysis and scan not available"
        $Script:SCALocalInstall = $False
    }

    # Check FPR Utility is on the path
    if ((Get-Command "FPRUtility.bat" -ErrorAction SilentlyContinue) -eq $null)
    {
        Write-Warning "Unable to find FPRUtility.bat in your PATH - issue summaries not available"
        $Script:SCALocalInstall = $False
    }

    # Check Report Generator is on the path
    if ((Get-Command "ReportGenerator.bat" -ErrorAction SilentlyContinue) -eq $null)
    {
        Write-Warning "Unable to find ReportGenerator.bat in your PATH - report generation not available"
        $Script:SCALocalInstall = $False
    }

    # Check Fortify Client is installed
    if ((Get-Command "fortifyclient.bat" -ErrorAction SilentlyContinue) -eq $null)
    {
        Write-Warning "fortifyclient.bat is not in your PATH - upload to SSC not available"
        $Script:SCALocalInstall = $False
    }

    # Check ScanCentral Client is installed
    if ((Get-Command "scancentral.bat" -ErrorAction SilentlyContinue) -eq $null)
    {
        if ($SCALocalInstall -eq $False) {
            Write-Warning "scancentral.bat is not in your PATH - cannot run local or remote scan"
        }
        $Script:ScanCentralClient = $False
    }

    Write-Host "Done."
}


function Test-ThirdPartyEnvironment {
    Write-Host "Validating Third Party Installation..."

    # Check Maven is on the path
    if ((Get-Command "mvn" -ErrorAction SilentlyContinue) -eq $null)
    {
        Write-Host
        throw "Unable to find mvn in your PATH"
    }

    # Check Sonatype is installed
    if ((Get-Command "nexus-iq-cli.exe" -ErrorAction SilentlyContinue) -eq $null)
    {
        Write-Host
        throw "Unable to find nexus-iq-cli.exe is not in your PATH"
    }

    Write-Host "Done."
}

function Get-SCAInstalled {
    $Script:SCALocalInstall
}

function Get-ScanCentralCientInstalled {
    $Script:ScanCentralClient
}
