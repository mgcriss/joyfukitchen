git clone https://github.com/mgcriss/joyfukitchen.git
echo "下载成功"
git checkout -b dev origin/dev
echo "获取分支dev成功"

git checkout -b dengou origin/dengou
echo "获取分支dengou成功"

git checkout dengou
echo "切换分支到dengou"
pause