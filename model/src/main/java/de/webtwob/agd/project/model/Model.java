package de.webtwob.agd.project.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.elk.graph.ElkNode;

@SuppressWarnings("exports")
public class Model {
	
	
	//TODO Thread 
	//TODO Find a good way to store the changes -> use de.webtwob.agd.project.api.util.GraphMapping 
	/**Greedy cycle break from before
	 * 
	 * @param graph
	 * @return
	 */
	public static List<ElkNode> getSteps(ElkNode graph) {
		List<ElkNode> steps = new LinkedList<ElkNode>();
        // order nodes

        // copy child list so we can remove already sorted ones
        List<ElkNode> children = new LinkedList<>(graph.getChildren());

        // sources at the beginning add to the end
        LinkedList<ElkNode> sourceList = new LinkedList<>();
        // sinks at the end add to the beginning
        LinkedList<ElkNode> sinkList = new LinkedList<>();
        
        
        // 0 Steps 
        while (!children.isEmpty()) {
            boolean found;

            // sort out source
            do {
                found = false;
                for (Iterator<ElkNode> iter = children.iterator(); iter.hasNext();) {
                    ElkNode node = iter.next();
                    // is node a Source given the currently present nodes in children
                    if (node.getIncomingEdges().parallelStream().map(Util::getSource).noneMatch(children::contains)) {
                        sourceList.addLast(node);
                        iter.remove(); // avoid ConcurrentModificationException
                        found = true;
                      //TODO Store "currently active" Property
                        //TODO Store sink Property and return step to Controler
                    }

                }

            } while (found);// stop when an iteration didn't found sinks

            // sort out sink
            do {
                found = false;
                for (Iterator<ElkNode> iter = children.iterator(); iter.hasNext();) {
                    
                    ElkNode node = iter.next();

                    // is node a Source given the currently present nodes in children
                    if (node.getOutgoingEdges().parallelStream().map(Util::getTarget).noneMatch(children::contains)) {
                        sinkList.addFirst(node);
                        iter.remove(); // avoid ConcurrentModificationException
                        found = true;
                      //TODO Store "currently active" Property
                      //TODO Store source Property and return step to Controler
                    }

                }

            } while (found);// stop when an iteration didn't found sinks
            
            
            //TODO Store "currently active" Property
            // find edge with max in-degree to out-degree difference
            ElkNode maxNode = null;
            int maxDiff = Integer.MIN_VALUE;
            for (Iterator<ElkNode> iter = children.iterator(); iter.hasNext();) {
                
                ElkNode curNode = iter.next();
              //TODO Store "currently active" Property
              //TODO Store "currently active edges" Property
                int curVal = curNode.getOutgoingEdges().size() - curNode.getIncomingEdges().size();
                if (curVal > maxDiff) {
                    maxDiff = curVal;
                    maxNode = curNode;
                }
            }

            // if we still had nodes add the one with max out to in diff to source list
            if (maxNode != null) {
                sourceList.addFirst(maxNode);
                children.remove(maxNode);
            }

        }

        // remove cycles
        List<ElkNode> combinedList = new LinkedList<>();
        combinedList.addAll(sourceList);
        combinedList.addAll(sinkList);

        graph.getContainedEdges().stream().forEach(e -> {
            // reverse all edges where the source Node index is higher than the target node index
            if (combinedList.indexOf(Util.getSource(e)) > combinedList.indexOf(Util.getTarget(e))) {
                Util.reverseEdge(e);
            }
        });

		
		
		return null;
	}
	
	

}
