echo off
chcp 65001

git checkout master
echo 切换到master
git merge dev
echo dev合并到master
git push origin master
echo 提交到master
pause