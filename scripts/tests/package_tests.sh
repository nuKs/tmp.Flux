#!/bin/bash
#
# @note aws doc `https://docs.aws.amazon.com/devicefarm/latest/developerguide/how-to-create-test-run.html`

set -ex

echo "@warning make sure you have npm-bundle installed"
echo "\t`npm install -g npm-bundle`"

# Copy minimal amount of file to run integration tests (so copy to aws device farm doesn't take too long)
mkdir test-package/
cp babel.config.js test-package/
cp package.test_integration.json test-package/package.json
mkdir test-package/__tests__/
cp -R test-integration/ test-package/__tests__/

# Open folder
cd test-package/

# Install packages (as req. by device farm)
npm install

# Bundle packages (aws requires a .tgz inside a .zip)
TGZ_BUNDLE=$(npm-bundle)
ZIP_OUTPUT=test-bundle.zip
zip -r ${ZIP_OUTPUT} ${TGZ_BUNDLE}

# mkdir test-bundle
# mv ${TGZ_BUNDLE} test-bundle/
# zip -r ${ZIP_OUTPUT} test-bundle
# rm -r test-bundle/

# Cleanup
cd ../
mv test-package/${ZIP_OUTPUT} ./
rm -r test-package/
