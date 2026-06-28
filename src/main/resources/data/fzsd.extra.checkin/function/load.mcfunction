# 加载签到榜扩展：FZ 计分板存在但扩展未安装时补安装；已安装时同步数据。
execute if data storage fzsd:install fzsd.module{scoreboard:1b} unless data storage fzsd:install fzsd.extra{checkin:1b} run function fzsd.extra.checkin:install
execute if data storage fzsd:install fzsd.extra{checkin:1b} run function fzsd.extra.checkin:sync_all
