# Fortify Demo App

This is a sample Java/Spring web application that can be used for the demonstration of Fortify SAST, DAST and SCA.
It is a cutdown "search" results/details page from a larger sample application 
[IWAPharmacyDirect](https://github.com/fortify-presales/IWAPharmacyDirect) and is kept deliberately small in size to reduce 
scan times for demos.

To use this demo in full you will need the following software installed:

* [Fortify Static Code Analyzer and Tools](https://www.microfocus.com/en-us/cyberres/application-security/static-code-analyzer)
* [Fortify Software Security Center](https://www.microfocus.com/en-us/cyberres/application-security/software-security-center)  
* *optional* A [Sonatype Nexus IQ Server](https://help.sonatype.com/iqserver) installation for Software Composition Analysis
* *optional* A [Fortify Source And Lib Scanner](https://marketplace.microfocus.com/fortify/content/fortify-sourceandlibscanner) installation
* *optional* A [Fortify ScanCentral SAST/DAST]() installation
* *optional* A Microsoft Azure Subscription and [Azure CLI](https://docs.microsoft.com/en-us/cli/azure/install-azure-cli) for deploying application to Azure

Setup
-----

First create a file called `.env` in the project root directory with content similar to the following:

```
# The applications URL - when running locally
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
# Change to your email address
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
# change the below to a unique name, e.g. "YOUR_INITIALS-fortify-demo-app"
AZURE_APP_NAME=KAL-fortify-demo-app
AZURE_REGION=eastus
```

Replace all the `XXX` and `YYY` values with values appropriate to your environment.

Please do NOT add this file to source control.

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

These have been "enabled" because they all have potential security issues that can be found by Fortify.

Deploy Application (Azure)
--------------------------

If you want to run the application in the cloud (so you can run a WebInspect span for example) you can deploy the application to Microsoft Azure along with its required infrastructure
by using the following (from a Windows command prompt):

```
az login [--tenant 856b813c-16e5-49a5-85ec-6f081e13b527]
az group create --name fortify-demo-rg --location eastus
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
az group delete --name fortify-demo-rg

```

Application Security Testing
----------------------------

As this is a Java/Spring application you can use it for demonstration of Fortify SAST/DAST
correlation. If you run the Fortify Static Code Analyzer scan using the below script it includes
the "`-Dcom.fortify.sca.rules.enable_wi_correlation=true`" command line switch that includes
potential findings that can be found via DAST scan in the resultant FPR created. When you run a
suitable ScanCentral DAST scan these results should be correlated in SSC. Please note as one
of these findings is a "Blind SQL Injection" (WebInspect Check Id: 11299) you will need to ensure that you are 
using a suitable WebInspect policy. An example "Critical and Highs Custom Policy" is included 
[here](etc/Critical-and-Highs-Custom.policy).

***Fortify Static Code Analyzer:***

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
- Path Manipulation  
- Insecure Randomness
- Empty Password in Configuration File

***Fortify ScanCentral SAST:***

To run a Fortify ScanCentral SAST scan you can use the included script `fortify-scancentral-sast.ps1` as follows:

```
powershell .\bin\fortify-scancentral-sast.ps1
```

***Fortify Software Composition Analysis:***

To run a Fortify Software Composition Analysis (Sonatype) scan you can use the included script `fortify-sourceandlibscanner.ps1`

```
powershell .\bin\fortify-sourceandlibscanner.ps1
```

***Fortify ScanCentral DAST:***

To run a Fortify ScanCentral DAST scan you can use the included script `fortify-scancentral-dast.ps1` as follows:

```
powershell .\bin\fortify-scancentral-dast.ps1
```

---

Kevin A. Lee (kadraman) - kevin.lee@microfocus.com
