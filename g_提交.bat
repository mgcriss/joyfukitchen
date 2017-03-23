echo off
chcp 65001
git add .
echo "添加成功，输入提交信息 = "
set /p mes=
echo 输入的信息为%mes%
echo 正在提交
git commit -m "%mes%"

git push origin dengou
echo 提交到
git checkout dev 

echo 合并到dev
git merge dengou
echo 提交到dev
git push origin dev
pause