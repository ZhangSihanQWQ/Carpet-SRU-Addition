scoreboard players operation @s sru_ci_dist = @s sru_ci_walk
scoreboard players operation @s sru_ci_dist -= @s sru_ci_w_base

scoreboard players operation #temp sru_ci_dist = @s sru_ci_sprt
scoreboard players operation #temp sru_ci_dist -= @s sru_ci_s_base
scoreboard players operation @s sru_ci_dist += #temp sru_ci_dist

execute if score @s sru_ci_dist matches 10000.. run function sru_checkin:success

