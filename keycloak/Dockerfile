FROM quay.io/keycloak/keycloak:26.2

# Copiar el realm y el tema personalizado
COPY realms /opt/keycloak/data/import
COPY themes /opt/keycloak/themes

ENTRYPOINT ["/opt/keycloak/bin/kc.sh"]
CMD ["start-dev", "--import-realm"]
