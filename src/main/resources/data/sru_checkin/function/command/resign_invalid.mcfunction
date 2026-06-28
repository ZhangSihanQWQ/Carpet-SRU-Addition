tellraw @s {"text":"补签参数无效：请使用 /trigger sru_ci_resign set 1..31（1=今天，2=昨天，31=30天前）。","color":"red"}
scoreboard players reset @s sru_ci_resign
scoreboard players enable @s sru_ci_resign
