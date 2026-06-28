tellraw @s {"text":"补签参数无效：请使用 /trigger sru_ci_resign set 0..30（0=今天，1=昨天，30=30天前）。","color":"red"}
scoreboard players reset @s sru_ci_resign
scoreboard players enable @s sru_ci_resign
