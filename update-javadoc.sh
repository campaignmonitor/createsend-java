#!/bin/sh

## Generates and updates javadoc for createsend-java, hosted at:
## http://campaignmonitor.github.com/createsend-java/doc/

BUILD_DOC=build/doc
BUILD_PAGES=build/gh-pages

set -e

rm -rf $BUILD_DOC $BUILD_PAGES
git clone git@github.com:campaignmonitor/createsend-java.git $BUILD_PAGES -b gh-pages
gradle doc
VERSION=$(basename $BUILD_DOC/*)
rsync -f 'exclude .git' -r --delete $BUILD_DOC/$VERSION/ $BUILD_PAGES/doc
cd $BUILD_PAGES
git add doc
git commit -m "Generated javadoc for $VERSION" --allow-empty
git push origin gh-pages
cd ../..
rm -rf $BUILD_DOC $BUILD_PAGES
