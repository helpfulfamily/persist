(sed  '/^\s*$/d'  config/application-pr.properties \
             | grep -v "PASSWORD" | grep -v "USER" \
             | sed -e 's/^/--from-literal=/' | tr "\n" ' ')