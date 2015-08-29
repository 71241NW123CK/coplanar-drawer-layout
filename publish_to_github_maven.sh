#!/usr/bin/env sh
echo "publishing to GitHub maven."

# determine if any git submodules already exist.
echo "determining if any git submodules already exist."
if [ -f .gitmodules ]; then
	echo "There are already git submodules."
	HAD_GIT_SUBMODULES=true
else
	echo "There are no git submodules."
	HAD_GIT_SUBMODULES=false
fi

GRADLE_ROOT_PATH=CoplanarDrawerLayout
PUBLISHING_DESTINATION_GIT_REPO_URL=git@github.com:71241NW123CK/maven-repo.git
PUBLISHING_DESTINATION_GIT_REPO_LOCAL_PATH=publish/maven

PUB_DST_REPO_URL=$PUBLISHING_DESTINATION_GIT_REPO_URL
PUB_DST_REPO_LOCAL_PATH=$PUBLISHING_DESTINATION_GIT_REPO_LOCAL_PATH

# set up publishing destination git repo as a git submodule
echo "setting up publishing destination git repo as a git submodule."
if [ -d $PUB_DST_REPO_LOCAL_PATH ]; then
	echo "Ruh-roh... $PUB_DST_REPO_LOCAL_PATH already exists.  Not going to proceed and screw up things.  Abort foetus."
	exit
fi

git submodule add $PUB_DST_REPO_URL $PUB_DST_REPO_LOCAL_PATH

if [ ! -d $PUB_DST_REPO_LOCAL_PATH ]; then
	echo "Ruh-roh... Failed to clone submodule from $PUB_DST_REPO_URL to $PUB_DST_REPO_LOCAL_PATH.  Abort foetus."
	exit
fi

# upload archives
echo "publishing artifacts to publishing destination."
pushd .
cd $GRADLE_ROOT_PATH
./gradlew clean uploadArchives
popd

# commit and push the updated publishing destination repo
echo "pushing the publishing repo."
pushd .
cd $PUB_DST_REPO_LOCAL_PATH
git add -A .
git commit -m "publishing to GitHub hosted maven repo."
git push
popd

# clean up the publinhing destination repo, restoring its superproject.
echo "cleaning up the publishing repo."
git reset head .gitmodules
if $HAD_GIT_SUBMODULES; then
	git add .gitmodules
else
	rm .gitmodules
fi
git config --remove-section "submodule.$PUB_DST_REPO_LOCAL_PATH"
git rm -f --cached $PUB_DST_REPO_LOCAL_PATH
rm -rf .git/modules/$PUB_DST_REPO_LOCAL_PATH
rm -rf $PUB_DST_REPO_LOCAL_PATH

echo "all done!"
