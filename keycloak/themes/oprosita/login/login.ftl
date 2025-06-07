<#import "template.ftl" as layout>
<@layout.registrationLayout displayInfo=true displayMessage=true displayRequiredFields=true>
    <#assign section = "header" />
    <#if section == "header">
        <h1>oPROsita</h1>
    </#if>

    <#assign section = "form" />
    <#if section == "form">
        <form id="kc-form-login" onsubmit="login.disabled = true; return true;" action="${url.loginAction}" method="post">
            <div class="form-group">
                <input type="text" id="username" name="username" class="form-control" placeholder="Usuario" autofocus value="${(login.username!'')}" />
            </div>
            <div class="form-group">
                <input type="password" id="password" name="password" class="form-control" placeholder="Contraseña" />
            </div>
            <div class="form-group">
                <button class="btn btn-primary btn-block" name="login" id="kc-login" type="submit">Iniciar sesión</button>
            </div>
        </form>
    </#if>
</@layout.registrationLayout>
