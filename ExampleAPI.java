/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package exampleapi;

/**
 *
 * @author jorge
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import org.openmarkov.core.exception.InvalidStateException;
import org.openmarkov.core.inference.InferenceAlgorithm;
import org.openmarkov.core.model.network.EvidenceCase;
import org.openmarkov.core.model.network.Finding;
import org.openmarkov.core.model.network.NodeType;
import org.openmarkov.core.model.network.ProbNet;
import org.openmarkov.core.model.network.ProbNode;
import org.openmarkov.core.model.network.Util;
import org.openmarkov.core.model.network.Variable;
import org.openmarkov.core.model.network.potential.TablePotential;
import org.openmarkov.inference.variableElimination.VariableElimination;
import org.openmarkov.io.probmodel.PGMXReader;

/**
 *
 * @author Jorge Mejia
 */
public class ExampleAPI {
  
    ProbNode probNode;

    private Object[] dat;
    private Object graph;
 
    public static void main(String[] args) {
     ExampleAPI ea=   new ExampleAPI();
     ea.obtenerdatos();
        
    }

    // Constants
    final private String bayesNetworkName = "examencopias.pgmx";

    // Constructor
    public ExampleAPI() {
  

    }



    public ProbNode getProbNode() {
        return probNode;
    }

    public void setProbNode(ProbNode probNode) {
        this.probNode = probNode;
    }

    public Object[] getDat() {
        return dat;
    }

    public void setDat(Object[] dat) {
        this.dat = dat;
    }

    public Object getGraph() {
        return graph;
    }

    public void setGraph(Object graph) {
        this.graph = graph;
    }



//Metodo que devuelve la lista con todos los nodos
    @SuppressWarnings("empty-statement")
   public List<ProbNode> obtenerdatos(){
         List<ProbNode> listPro=null;
               try {
            InputStream file = new FileInputStream(new File( "C:/Users/jorge/Documents/redesbayesianas/aprobar modulorespaldo1.pgmx"));
            PGMXReader pgmxReader = new PGMXReader();
            ProbNet probNet = pgmxReader.loadProbNet(file, bayesNetworkName).getProbNet();
            graph=probNet.getPotentialsByType(NodeType.COST);
            System.out.println(probNet.getNumNodes());
// Almacena los nodos con sus caracteristicas
          listPro = probNet.getProbNodes();
//            for (int i = 0; i < listPro.size(); i++) {
//                probNode = listPro.get(i);
//      
//                System.out.println("NOMBRE " + probNode.getName());
//                System.out.println("PROBNET " + probNode.getProbNet());
//                System.out.println("RELEVANCE" + probNode.getRelevance());
//                System.out.println("TIPO DE NODO " + probNode.getNodeType().toString());
//                System.out.println("UTYLITY " + probNode.getUtilityFunction());
//              listnodo.add(new nodo(probNode.getName(), probNode.getProbNet()+"",probNode.getRelevance()+"", probNode.getNodeType().toString(), probNode.getUtilityFunction()+""));
//      
//            
//            }
//          
        
               
               

                        
               

          
          
            

                    EvidenceCase evidence = new EvidenceCase();
//
//			// The first finding we introduce is the presence
//			// of the symptom 
			evidence.addFinding(probNet, "Aprobar modulo", "si");
//
//			// Create an instance of the inference algorithm
//			// In this example, we use the variable elimination algorithm
			InferenceAlgorithm variableElimination = new VariableElimination(probNet);
//
//			// Add the evidence to the algorithm
//			// The resolution of the network consists of finding the
//			// optimal policies. 
//			// In the case of a model that does not contain decision nodes
//			// (for example, a Bayesian network), there is no difference between
//			// pre-resolution and post-resolution evidence, but if the model
//			// contained decision nodes (for example, an influence diagram)
//			// evidence introduced before resolving the network is treated 
//			// differently from that introduce afterwards.
			variableElimination.setPreResolutionEvidence(evidence);
//
//			// We are interested in the posterior probabilities of the diseases
			Variable disease1 = probNet.getVariable("Aprobar modulo");
//			Variable disease2 = probNet.getVariable("Disease 2");
			ArrayList<Variable> variablesOfInterest = new ArrayList<Variable>();
			variablesOfInterest.add(disease1);
//			variablesOfInterest.add(disease2);
//
//			// Compute the posterior probabilities
			HashMap<Variable, TablePotential> posteriorProbabilities = 
					variableElimination.getProbsAndUtilities();
//
//			// Print the posterior probabilities on the standard output
//			printResults(evidence, variablesOfInterest, posteriorProbabilities);
//
//			// Add a new finding and do inference again
//			// (We see that the presence of the sign confirms the presence
//			// of Disease 1 with high probability and explains away Disease 2)
			evidence.addFinding(probNet, "Aprobar modulo", "si");
			posteriorProbabilities = variableElimination.getProbsAndUtilities(variablesOfInterest);
		//printResults(evidence, variablesOfInterest, posteriorProbabilities);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return listPro;
           
   }

    /**
     * Print the posterior probabilities of the variables of interest on the
     * standard output
     *
     * @param evidence. <code>EvidenceCase</code> The set of observed findings
     * @param variablesOfInterest. <code>ArrayList</code> of
     * <code>Variable</code> The variables whoseposterior probability we are
     * interested in
     * @param posteriorProbabilities. <code>HashMap</code>. Each
     * <code>Variable</code> is mapped onto a <code>TablePotential</code>
     * containing its posterior probability
     */
    public void printResults(EvidenceCase evidence, ArrayList<Variable> variablesOfInterest,
            HashMap<Variable, TablePotential> posteriorProbabilities) {
        // Print the findings
       String str= "Evidence:"+"\n";
       String str2=null;
       String str3=null;
       String str9=null;
       
        for (Finding finding : evidence.getFindings()) {
            str2="  " + finding.getVariable() + ": "+"\n";
        str3=finding.getState();
             
        }
       String str4=str+"\n"+str2+"\n"+str3+"\n";
        // Print the posterior probability of the state "present" of each variable of interest
        String str5="Posterior probabilities: "+"\n";
        for (Variable variable : variablesOfInterest) {
            double value;
            TablePotential posteriorProbabilitiesPotential = posteriorProbabilities.get(variable);
            String str6="PROBABILIDAD"+posteriorProbabilities.values()+"\n";
            String str7="  " + variable + ": "+"\n";
            int stateIndex = -1;
            try {
                stateIndex = variable.getStateIndex("si");
                value = posteriorProbabilitiesPotential.values[stateIndex];
              String str8=Util.roundedString(value, "0.001")+"\n";
              str9=str5+str6+str8;  
              
            } catch (InvalidStateException e) {
                System.err.println("State \"present\" not found for variable \""
                        + variable.getName() + "\".");
                e.printStackTrace();
            }
        }
        System.out.println();
        String union=str4+"\n"+str9;
        JOptionPane.showMessageDialog(null, union);
     
        
    }
}
