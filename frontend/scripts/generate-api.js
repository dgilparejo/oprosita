const { execSync } = require('child_process');
const path = require('path');
require('dotenv').config({ path: path.resolve(__dirname, '../../.env') });

const username = process.env.DOCKERHUB_USERNAME || 'lnuclear';
const version = process.env.OPENAPI_BACKEND_VERSION || '1.3.6';

// Ruta raíz del proyecto
const rootPath = path.resolve(__dirname, '../..'); // C:/.../oprosita
const openapiPath = path.join(rootPath, 'openapi/openapi.yml').replace(/\\/g, '/');
const outputPath = path.join(rootPath, 'frontend/src/app/api').replace(/\\/g, '/');

const dockerCmd = `docker run --rm -v "${rootPath}:/local" ${username}/openapi-frontend:${version} openapi-generator generate -i /local/openapi/openapi.yml -g typescript-angular -o /local/frontend/src/app/api --additional-properties=providedIn=root --skip-validate-spec`;

try {
  console.log(`Generating API client from: ${openapiPath}`);
  execSync(dockerCmd, { stdio: 'inherit' });
} catch (err) {
  console.error('Failed to generate OpenAPI client:', err.message);
  process.exit(1);
}
