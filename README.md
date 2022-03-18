# Fortify Demo App

This is a sample Java/Spring web application that can be used for the demonstration of Fortify SAST, DAST and SCA.
It is basically a cutdown "search" page from a larger sample application [IWAPharmacyDirect](https://github.com/fortify-presales/IWAPharmacyDirect) and
is kept deliberately small in size to reduce scan times for demos.

To use this demo in full you will need the following software installed:

* [Fortify Static Code Analyzer and Tools](https://www.microfocus.com/en-us/cyberres/application-security/static-code-analyzer)
* [Fortify Source and Lib Scanner](https://marketplace.microfocus.com/fortify/content/fortify-sourceandlibscanner)
* [Fortify Software Security Center](https://www.microfocus.com/en-us/cyberres/application-security/software-security-center)  
* *optional* [Fortify Software Composition Analysis](https://www.microfocus.com/en-us/cyberres/application-security/software-composition-analysis)
* *optional* [Fortify ScanCentral SAST/DAST]() installation
* *optional* [Azure PowerShell Module](https://docs.microsoft.com/en-us/powershell/azure/install-az-ps) for deploying application to Azure

Setup
-----

First create a file called `.env` in the project root directory with content similar to the following:

```
# The applications URL
APP_URL=http://localhost:8088
# The URL of Software Security Center
SSC_URL=http://fortify.xxx.xxx
SSC_USERNAME=admin
SSC_PASSWORD=XXX
# SSC Authentication Token (recommended to use CIToken)
SSC_AUTH_TOKEN=XXXXX
# Name of the application in SSC
SSC_APP_NAME=FortifyDemoApp
# Name of the application version in SSC
SSC_APP_VER_NAME=main
SCANCENTRAL_CTRL_URL=http://fortify.xxx.xxx/scancentral-ctrl
SCANCENTRAL_CTRL_TOKEN=XXXXX
SCANCENTRAL_POOL_ID=00000000-0000-0000-0000-000000000002
SCANCENTRAL_EMAIL=xxx.xxx@fortify.com
SCANCENTRAL_DAST_API=http://fortify.xxx.xxx:64814
SCANCENTRAL_DAST_CICD_TOKEN=XXXXX
# Nexus IQ Lifecycle
NEXUS_IQ_URL=http://fortify.xxx.xxx:8070/
NEXUS_IQ_AUTH=XXX:YYY
NEXUS_IQ_APP_ID=FortifyDemoApp
# Fortify on Demand
FOD_API_URL=https://api.xxx.fortify.com
FOD_API_KEY=XXX
FOD_API_SECRET=XXX
# Azure (Resource Manager)
AZURE_SUBSCRIPTION_ID=XXX
AZURE_RESOURCE_GROUP=fortify-demo-rg
AZURE_APP_NAME=fortify-demo-app
AZURE_REGION=eastus
```

Replace all the `XXX` and `YYY` values with values appropriate to your environment.

Please do NOT add this file to source control.

Run Application (locally)
-------------------------

You can the run the application locally using the following:

```
.\gradlew bootRun
```

The application should then be available at the URL `http://localhost:8088`. If it fails to start make sure you have
no other applications running on port 8088.

Deploy Application and Infrastructure
-------------------------------------

You can deploy the application to Microsoft Azure along with its required infrastructure
by using the following (from a PowerShell command prompt):

```
Connect-AzAccount
New-AzResourceGroup -Name fortify-demo-rg -Location eastus
.\gradlew azureWebAppDeploy
```

Replace `eastus` with your own desired region.

You can navigate to your [Azure portal](https://portal.azure.com/#home) to see the built infrastructure and to
the deployed web application using the URL output shown from the `azureWebAppDeploy task`.

Remove Application and Infrastructure
-------------------------------------

To clean up all the resources you can execute the following (from a PowerShell console):

```
Remove-AzResourceGroup -Name fortify-demo-rg
```

Application Security Testing
----------------------------

**Fortify Static Code Analyzer:**

To run a Fortify Static Code Analyzer scan you can use the included script `fortify-sca.ps1` as follows:

```
powershell .\bin\fortify-sca.ps1
```

This will scan the applications source code.

An example results file (in PDF) is available [here](samples/FortifyDemoApp.pdf).

To view the full results yourself you can use:

```
auditworkbench .\FortifyDemoApp.fpr
```

There should be a number of issues including (but not limited to):

- SQL Injection
- JSON Injection
- Insecure Randomness
- Empty Password in Configuration File
- Path Manipulation

**Fortify ScanCentral SAST:**

To run a Fortify ScanCentral SAST scan you can use the included script `fortify-scancentral-sast.ps1` as follows:

```
powershell .\bin\fortify-scancentral-sast.ps1
```

**Fortify Software Composition Analysis**

To run a Fortify Software Composition Analysis scan you can use the included script `fortify-sourceandlibscanner.ps1`

```
powershell .\bin\fortify-sourceandlibscanner.ps1
```

**Fortify ScanCentral DAST:**

To run a Fortify ScanCentral DAST scan you can use the included script `fortify-scancentral-dast.ps1` as follows:

```
powershell .\bin\fortify-scancentral-dast.ps1
```

---

Kevin A. Lee (kadraman) - kevin.lee@microfocus.com
