FROM quay.io/keycloak/keycloak:24.0.1

# Copiar el realm y el tema personalizado
COPY data/realms /opt/keycloak/data/import
COPY themes /opt/keycloak/themes

ENTRYPOINT ["/opt/keycloak/bin/kc.sh"]
CMD ["start-dev", "--import-realm"]
