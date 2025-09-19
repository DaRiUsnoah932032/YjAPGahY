// 代码生成时间: 2025-09-20 04:14:18
package com.example.processmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
@RestController
public class ProcessManager {

    // A simple in-memory store to keep track of processes
    private final ConcurrentHashMap<String, Process> processMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        SpringApplication.run(ProcessManager.class, args);
    }

    // Start a new process and return its ID
    @PostMapping("/startProcess")
    public String startProcess(@RequestBody ProcessDetails processDetails) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(processDetails.getCommand().split(" "));
            Process process = processBuilder.start();
            String processId = String.valueOf(process.hashCode());
            processMap.put(processId, process);
            return processId;
        } catch (Exception e) {
            // Log and handle the exception
            return "Error starting process: " + e.getMessage();
        }
    }

    // Terminate a process by its ID
    @PostMapping("/terminateProcess")
    public String terminateProcess(@RequestBody ProcessId processId) {
        try {
            Process process = processMap.remove(processId.getId());
            if (process != null) {
                process.destroy();
                return "Process terminated successfully.";
            } else {
                return "Process not found.";
            }
        } catch (Exception e) {
            // Log and handle the exception
            return "Error terminating process: " + e.getMessage();
        }
    }

    // Get the status of a process by its ID
    @GetMapping("/getProcessStatus\)
    public String getProcessStatus(@RequestBody ProcessId processId) {
        Process process = processMap.get(processId.getId());
        if (process != null) {
            return "Process is running with status: " + process.isAlive();
        } else {
            return "Process not found.";
        }
    }

    // Helper classes for request bodies
    public static class ProcessDetails {
        private String command;

        public String getCommand() {
            return command;
        }

        public void setCommand(String command) {
            this.command = command;
        }
    }

    public static class ProcessId {
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
