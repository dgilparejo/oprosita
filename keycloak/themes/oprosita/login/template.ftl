<#macro registrationLayout displayInfo=false displayMessage=true displayRequiredFields=false>
    <!DOCTYPE html>
    <html lang="es">
    <head>
        <meta charset="utf-8">
        <title>Login - oPROsita</title>
        <link rel="stylesheet" href="${url.resourcesPath}/css/styles.css" type="text/css" />
        <meta name="viewport" content="width=device-width, initial-scale=1">
    </head>
    <body>

    <div id="kc-content">
        <#if displayMessage && message?has_content>
            <div class="alert ${message.type}">
                ${message.summary}
            </div>
        </#if>

        <#nested>
    </div>

    </body>
    </html>
</#macro>
