(function () {
    var PLATFORM_ID = "maha-health_platform_20200316113815452-54e2c8d3eaf169c",
        DOMAIN = "maha-health",
        LANGUAGE = "en";
    window.helpshiftConfig = {
        platformId: PLATFORM_ID,
        domain: DOMAIN,
        language: LANGUAGE,
    };
}) ();
!function(t,e){if("function"!=typeof window.Helpshift){var n=function(){n.q.push(arguments)};n.q=[],window.Helpshift=n;var i,a=t.getElementsByTagName("script")[0];if(t.getElementById(e))return;i=t.createElement("script"),i.async=!0,i.id=e,i.src="https://webchat.helpshift.com/webChat.js";var o=function(){window.Helpshift("init")};window.attachEvent?i.attachEvent("onload",o):i.addEventListener("load",o,!1),a.parentNode.insertBefore(i,a)}else window.Helpshift("update")}(document,"hs-chat");
Helpshift("setInitialUserMessage", "I want to know about Coronavirus / COVID-19. Please help!");
Helpshift("addEventListener", "conversationEnd", function () {
    Helpshift("setInitialUserMessage", "I want to know about Coronavirus / COVID-19. Please help!");
});
Helpshift("setCustomIssueFields", {
    // Key of the Custom Issue Field
    "vedang_site": {
        // Type of Custom Issue Field
        type: "checkbox",
        // Value to set for Custom Issue Field
        value: true
    }
});
