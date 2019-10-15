import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 *
 * @author Judith
 */
public class InputParameters {
    public static String systemName = "", path = "", pathRegular = "", pathMicro = "", programmingLanguage = "";
    public static int lastRevision;

    public void setParameters(String sysName){
        try{
            int flag = 0;

            if(sysName.equals("Ctags")){
                systemName = sysName;
                lastRevision = 774;
                path = "F:/Deckard_Clones/Ctags/Repository/version-";
                programmingLanguage = "C";
            }
            else if(sysName.equals("Brlcad")){
                systemName = sysName;
                lastRevision = 735;
                path = "F:/Deckard_Clones/Brlcad/Repository/version-";
                programmingLanguage = "C";
            }
            else if(sysName.equals("Freecol")){
                systemName = sysName;
                lastRevision = 1950;
                path = "F:/Deckard_Clones/Freecol/Repository/version-";
                programmingLanguage = "Java";
            }
            else if(sysName.equals("Carol")){
                systemName = sysName;
                lastRevision = 1700;
                path = "F:/Deckard_Clones/Carol/Repository/version-";
                programmingLanguage = "Java";
            }
            else if(sysName.equals("Jabref")){
                systemName = sysName;
                lastRevision = 1545;
                path = "F:/Deckard_Clones/Jabref/Repository/version-";
                programmingLanguage = "Java";
            }
            else if(sysName.equals("Select")){
                System.out.println("Please select a valid subject system.");
                flag = 1;
            }

            if(flag == 0)
                System.out.println("This is inside setParameters systemName = " + systemName + " Programming language = " + programmingLanguage + " Path = " + path + " Regular path = " + pathRegular
                        + " Micro path = " + pathMicro + " Last revision = " + lastRevision);

        }catch(Exception e){
            System.out.println("error in method setParameters = " + e);
            e.printStackTrace();
        }
    }

    public void getParameters(){
        try{
            System.out.println("Inside getParameters, System name = " + systemName);
        }catch(Exception e){
            System.out.println("error in method getParameters = " + e);
            e.printStackTrace();
        }
    }

    public void IdentifyingRegularMicroClones(int rev){
        // The purpose of this method is to take the output file of Deckard as input and separate regular and micro clones and then save it in two separate files.

        try {
            File file = new File(InputParameters.path + rev + "/clusters/cluster_vdb_30_5_allg_0.95_30"); //All Type

            if (file.exists()) {

                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(InputParameters.path + rev + "/clusters/cluster_vdb_30_5_allg_0.95_30"))); // All Type

                String str = "";
                String[] str1 = new String[100];
                int i = 0;

                while((str = br.readLine()) != null){
                    str1[i] = str.split(" ")[3].trim();
                    System.out.println("str1 = " + str1[i]);
                    i++;
                }
            }
        }catch (Exception e){
            System.out.println("Error in method IdentifyingRegularMicroClones" + e);
            e.printStackTrace();
        }


    }

}
