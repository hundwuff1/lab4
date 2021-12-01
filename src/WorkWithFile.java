import java.io.*;


public class WorkWithFile {

    public static String readFile(String filePath){
        StringBuilder returnLine= new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
              returnLine.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return returnLine.toString();
    }

    public static void writeFile(String filePath,String text){
        try(FileWriter writer = new FileWriter(filePath, false))
        {
            writer.write(text);
            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
        System.out.println("Запись завершена");
    }

    public static byte[] readBinary(String filepath){

        File file=new File(filepath);
        try {
            FileInputStream fileInputStream=new FileInputStream(file);
            return fileInputStream.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void writeFile(String filepath,byte[] text){
        File file=new File(filepath);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(file);
            fos.write(text);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
