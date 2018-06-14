import de.webtwob.agd.project.api.interfaces.IAlgorithm;

module de.webtwob.agd.project.view {

	// java requires
	// java requires transitive
	requires transitive java.desktop;

	// external requires
	requires org.eclipse.elk.graph;
	requires org.eclipse.emf.ecore;

	// internal requires

	// internal requires transitive
	requires transitive de.webtwob.agd.project.api;

	// exports
	exports de.webtwob.agd.project.view;
	exports de.webtwob.agd.project.view.panel;
	
	uses IAlgorithm;

}
