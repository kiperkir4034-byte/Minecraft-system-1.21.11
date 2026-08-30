package recovered.fabric.nativebridge;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Locale;

public final class NativeLibrary {
   private NativeLibrary() {
   }

   public static boolean enabled() {
      String osName = System.getProperty("os.name", "").toLowerCase(Locale.ROOT);
      return osName.contains("windows") && Boolean.parseBoolean(System.getProperty("systemdlc.native", "true"));
   }

   public static Path extract() throws IOException {
      String resource = "/native/windows-x86_64/systemdlc.dll";

      Path path;
      try (InputStream input = NativeLibrary.class.getResourceAsStream(resource)) {
         if (input == null) {
            throw new IOException("Native library resource is missing");
         }

         Path directory = Files.createTempDirectory("systemdlc-native-");
         Path library = directory.resolve("systemdlc.dll");
         Files.copy(input, library, StandardCopyOption.REPLACE_EXISTING);
         directory.toFile().deleteOnExit();
         library.toFile().deleteOnExit();
         path = library;
      }

      return path;
   }
}
