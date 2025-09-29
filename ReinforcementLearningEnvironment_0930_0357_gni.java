// 代码生成时间: 2025-09-30 03:57:22
package com.example.reinforcementlearning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class ReinforcementLearningEnvironment {

    private static final int MAX_STATES = 10; // 假设有10个状态
    private static final int ACTIONS = 3; // 假设有3种动作
    private static final int MAX_STEPS = 100; // 每个episode的最大步数
    private static final Random random = new Random();
    private int state = 0; // 当前状态
    private int step = 0; // 当前步骤
    private Map<Integer, Integer> rewards = new HashMap<>(); // 存储每个状态的奖励

    public static void main(String[] args) {
        SpringApplication.run(ReinforcementLearningEnvironment.class, args);
    }

    // 重置环境到初始状态
    public int reset() {
        state = 0;
        step = 0;
        return state;
    }

    // 执行动作并获得下一个状态的奖励
    public int step(int action) {
        if (step >= MAX_STEPS) {
            throw new RuntimeException("Episode has ended.");
        }

        int reward = 0;
        switch (action) {
            case 0: // Action 0
                reward = random.nextInt(5);
                break;
            case 1: // Action 1
                reward = random.nextInt(5) + 5;
                break;
            case 2: // Action 2
                reward = random.nextInt(5) + 10;
                break;
            default:
                throw new IllegalArgumentException("Invalid action.");
        }

        state = (state + action) % MAX_STATES; // 更新状态
        step++;

        rewards.put(state, reward); // 记录奖励
        return reward;
    }

    // 获取当前状态
    public int getState() {
        return state;
    }

    // 获取每个状态的奖励
    public Map<Integer, Integer> getRewards() {
        return rewards;
    }

    // 检查是否达到最大步数
    public boolean isTerminal() {
        return step >= MAX_STEPS;
    }
}
