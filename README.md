# Fortify Demo App

This is a sample Java/Spring web application that can be used for the demonstration of 
[Fortify SAST](https://www.microfocus.com/en-us/cyberres/application-security/static-code-analyzer) and
[Fortify DAST](https://www.microfocus.com/en-us/cyberres/application-security/fortify-dast). 
It is a cut down "search" results/details page from a larger sample application 
[IWA-Java](https://github.com/fortify/IWA-Java) and is kept deliberately small for demos.

To use this demo in full you will need:

* an install of Fortify ScanCentral SAST, DAST and Software Security center
* an install [Fortify CLI installation](https://fortify-ps.github.io/fcli/)
* *optional* A Microsoft Azure Subscription and [Azure CLI](https://docs.microsoft.com/en-us/cli/azure/install-azure-cli) for deploying application to Azure

Run Application (locally)
-------------------------

You can the run the application locally using the following:

```
gradlew bootRun
```

The application should then be available at the URL `http://localhost:8088`. If it fails to start,
make sure you have no other applications running on port 8088. There are only a few features that are
functional in this version of the app:

 - you can type in some keywords in the search box, e.g. "alphadex" to filter results
 - you can click on any search result to navigate to a details page
 - you can download a datasheet PDF from a details page
 - you can subscribe to the newsletter by entering an email address in the input field of the footer
 - you can login/logout (user credentials are: admin/password, user1/password or user2/password)

These have been "enabled" because they all have potential security issues that can be found by Fortify.

Deploy Application (Azure)
--------------------------

If you want to run the application in the cloud (to run a ScanCentral DAST scan) you can deploy the application 
to Microsoft Azure along with its required infrastructure by using the following (from a Windows command prompt):

```
az login [--tenant XXXX]
az group create --name [YOUR_INITIALS]-fortify-demo-rg --location eastus
gradlew azureWebAppDeploy
```

Replace `eastus` with your own desired region and make sure in the `.env` file you have
set `AZURE_APP_NAME` to a unique value.

You can navigate to your [Azure portal](https://portal.azure.com/#home) to see the built infrastructure and to
the deployed web application using the URL output shown from the `azureWebAppDeploy task`.

Remove Application and Infrastructure
-------------------------------------

To clean up all the resources you can execute the following (from a Windows command prompt):

```
az group delete --name [YOUR_INITIALS]-fortify-demo-rg

```

Application Security Testing
----------------------------

### Creating an environment (.env) file

Most of the following examples need environment and user specific credentials. These are loaded from a file called `.env`
in the project root directory. This file is not created by default (and should never be stored in source control). An example
with all of the possible settings for the following scenarios is illustrated below:

```
# Application URL (locally)
APP_NAME=FortifyDemoApp
APP_URL=http://localhost:8088
# Software Security Center
SSC_URL=http://[YOUR-SSC-SERVER]
SSC_USERNAME=admin
SSC_PASSWORD=password
SSC_AUTH_TOKEN=XXX
SSC_APP_NAME=FortifyDemoApp
SSC_APP_VER_NAME=main
# ScanCentral SAST/DAST
SCANCENTRAL_CTRL_URL=http://[YOUR-SCANCENTRAL-SERVER]/scancentral-ctrl
SCANCENTRAL_CTRL_TOKEN=XXX
SCANCENTRAL_POOL_ID=00000000-0000-0000-0000-000000000002
SCANCENTRAL_EMAIL=info@microfocus.com
SCANCENTRAL_DAST_API=http://[YOUR-SCANCENTRAL-DAST-SERVER]/api/
# ScanCentral FAST
FAST_EXE=C:\\Program Files\\Micro Focus WIRC Server\\Fast.exe
FAST_PORT=8087
FAST_PROXY=127.0.0.1:8087
```

### SAST using Fortify SCA command line

There is an example PowerShell script [fortify-sast.ps1](bin/fortify-sast.ps1) that you can use to execute static application security testing
via [Fortify SCA](https://www.microfocus.com/en-us/products/static-code-analysis-sast/overview).

```PowerShell
.\bin\fortify-sast.ps1 -SkipSSC
```

This script runs a `sourceanalyzer` translation and scan on the project's source code. It creates a Fortify Project Results file called `IWAPharmacyDirect.fpr`
which you can open using the Fortify `auditworkbench` tool:

```PowerShell
auditworkbench.cmd .\FortifyDemoApp.fpr
```

It also creates a PDF report called `FortifyDemoApp.pdf` and optionally
uploads the results to [Fortify Software Security Center](https://www.microfocus.com/en-us/products/software-security-assurance-sdlc/overview) (SSC).

In order to upload to SSC you will need to have entries in the `.env` similar to the following:

```
SSC_URL=http://localhost:8080/ssc
SSC_AUTH_TOKEN=28145aad-c40d-426d-942b-f6d6aec9c56f
SSC_APP_NAME=FortifyDemoApp
SSC_APP_VER_NAME=main
```

The `SSC_AUTH_TOKEN` entry should be set to the value of a 'CIToken' created in SSC _"Administration->Token Management"_.

### SAST using Fortify ScanCentral SAST

There is a PowerShell script [fortify-scancentral-sast.ps1](bin\fortify-scancentral-sast.ps1) that you can use to package
up the project and initiate a remote scan using Fortify ScanCentral SAST:

```PowerShell
.\bin\fortify-scancentral-sast.ps1
```

In order to use ScanCentral SAST you will need to have entries in the `.env` similar to the following:

```
SSC_URL=http://localhost:8080/ssc
SSC_AUTH_TOKEN=6b16aa46-35d7-4ea6-98c1-8b780851fb37
SSC_APP_NAME=FortifyDemoApp
SSC_APP_VER_NAME=main
SCANCENTRAL_CTRL_URL=http://localhost:8080/scancentral-ctrl
SCANCENTRAL_CTRL_TOKEN=96846342-1349-4e36-b94f-11ed96b9a1e3
SCANCENTRAL_POOL_ID=00000000-0000-0000-0000-000000000002
SCANCENTRAL_EMAIL=test@test.com
```

The `SSC_AUTH_TOKEN` entry should be set to the value of a 'CIToken' created in SSC _"Administration->Token Management"_.

### DAST using Fortify ScanCentral DAST

To carry out a ScanCentral DAST scan you should first "deploy" the application using one of the steps described above.
Then you can start a scan using the provided PowerShell script [fortify-scancentral-dast.ps1](bin\fortify-scancentral-dast.ps1).
It can be invoked via the following from a PowerShell prompt:

```PowerShell
.\bin\fortify-scancentral-dast.ps1 -ApiUri 'SCANCENTRAL_DAST_API' -Username 'SSC_USERNAME' -Password 'SSC_PASSWORD' `
    -CiCdToken 'CICD_TOKEN_ID'
``` 

where `SCANCENTRAL_DAST_API` is the URL of the ScanCentral DAST API configured in SSC and
`SSC_USERNAME` and `SSC_PASSWORD` are the login credentials of a Software Security Center user who is permitted to
run scans. Finally, `CICD_TOKEN_ID` is the "CICD identifier" of the "Scan Settings" you have previously created from the UI.

---

Kevin A. Lee (kadraman) - kevin.lee@microfocus.com
