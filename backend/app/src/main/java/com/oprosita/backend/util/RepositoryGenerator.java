package com.oprosita.backend.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RepositoryGenerator {
    public static void main(String[] args) throws IOException {
        /*
        String modelPath = "target/generated-sources/openapi/src/main/java/com/oprosita/backend/model/generated";
        String repoPath = "src/main/java/com/oprosita/backend/repository";

        File repoDir = new File(repoPath);
        if (!repoDir.exists()) {
            repoDir.mkdirs();
        }

        File modelDir = new File(modelPath);
        File[] modelFiles = modelDir.listFiles((dir, name) -> name.endsWith(".java"));

        if (modelFiles == null) return;

        for (File file : modelFiles) {
            String className = file.getName().replace(".java", "");
            File repoFile = new File(repoPath + "/" + className + "Repository.java");

            if (!repoFile.exists()) {
                try (FileWriter writer = new FileWriter(repoFile)) {
                    writer.write("package com.oprosita.backend.repository;\n\n");
                    writer.write("import com.oprosita.backend.model." + className + ";\n");
                    writer.write("import org.springframework.data.jpa.repository.JpaRepository;\n");
                    writer.write("import org.springframework.stereotype.Repository;\n\n");
                    writer.write("@Repository\n");
                    writer.write("public interface " + className + "Repository extends JpaRepository<" + className + ", Long> {\n");
                    writer.write("}\n");
                }
                System.out.println("Generated: " + repoFile.getPath());
            }
        }
        */
}
}