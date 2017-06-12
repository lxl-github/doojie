/////////////////////////////////////////////////////////////
// DigitalChina Corp. 2001-2005 CopyRight.
//
// Author: Ma GuoHai
//
// Source: tree.js
//
// Function: JavaScript tree view for IE5.0 up
//
// Tree method:
//   void setLinkPath();
//   void setImagePath();
//   void setExpandDepth();
//   void setShowExpanders();
//   void setTargetFrame();
//   void setShowRoot();
//   addRoot(content, link, icon, open_icon);
//
// Node method:
//   addItem(content, link, icon, open_icon);
//
// Usage:
// 		tree = new Tree();
// 		root = addRoot(description, link, icon, open_icon);
// 		itm1 = root.addItem(descirption, link, icon, open_icon);
// 		itm2 = root.addItem(descirption, link, icon, open_icon);
// 		tree.show();
//
/////////////////////////////////////////////////////////////

var nodeIndex = new Array();	//Global node index for expandCompressNode


function Tree() {
	this.root = null;
	this.builded = false;
	this.showRoot = false;
	this.maxNodes = 0;
	this.expandDepth = 1;
	this.imagePath = "";
	this.linkPath = "";
	this.showExpanders = true;
	this.blankIcon = "blank.gif";  
	this.plusIcon = "lplus.gif";  
	this.minusIcon = "lminus.gif"; 
	this.targetFrame = "";
	
	

	this.addRoot = addRoot;
	this.show = showTree;	
	this.setLinkPath = setLinkPath;
	this.setExpandDepth = setExpandDepth;
	this.setShowExpanders = setShowExpanders;
	this.setTargetFrame = setTargetFrame;
	this.setImagePath = setImagePath;
	this.setShowRoot = setShowRoot;
	this.setplusIcon = setplusIcon;
	this.setminusIcon = setminusIcon;
}

/////////////////////////////////////////////////////////////
// showTree()
//   Display the tree. if not builded, build first
//
// Inputs:
//   static - if the tree show in static mode
//   	= null 	show static
//   	= true	show static
//   	= false	show dynamic
//   None.
// Output:
//   Tree is displayed in the browser.
/////////////////////////////////////////////////////////////
function showTree(static) {	
	var node = this.root;	

	if (!this.builded) {
		this.builded = true;

		if (static == null || !static) {
			this.root.build();
						
			if (this.showRoot) {			
				document.all["Item" + node.id].style.display="block";
			} else {
				document.all["Item" + node.id].style.display="none";
			}
    
			node.expandChildren(this.expandDepth);
			this.expandDepth = null;
		} else {
			this.root.buildStatic();
		}
	}
}


/////////////////////////////////////////////////////////////
// Define a tree node
//
// Inputs:
//   tree - tree control the node belong
//   icon - name of the icon eg. /file/btn.gif
//   content - node or foler content (can be HTML text)
//   link - link for the name and icon
//   parent - parent node
//   id - id assigned to the node
//   level - indent level of node
//   is_selectable - if the node need link
/////////////////////////////////////////////////////////////
function TreeNode(tree, content, link,destFrame, icon, open_icon, parent, id, level, is_selectable) {	
	this.tree = tree;			  // Tree control
	this.icon = icon;			  // Content Icon
	this.closedFolder = icon;	  // Content close icon
	this.openFolder = open_icon;  // Content open icon
	this.link = link;			  // Content Link	
	this.level = level;			  // Nodes Level wrt root node
	this.content = content;		  // Content to be displayed
	this.parent = parent;		  // Parent TreeNode
	this.children = new Array;	  // Children of the current TreeNode
	this.childCount = 0;		  // Number of children of this TreeNode
	this.id = id;				  // TreeNode Id number
	this.expanded = false;		  // Are the children visible
	this.visible = false;		  // Is the node itself visiable
	this.PSrc = null;			  // Plus icon source url
	this.MSrc = null;			  // Minus icon source url
	this.button = null;			  // Browser image object to the PM button
	this.img = null;			  // Browser image object to the icon
	this.selectable = is_selectable; // Item is Selectable?
	
	this.blankIcon = tree.blankIcon;
	this.showExpanders = tree.showExpanders;
	this.plusIcon = tree.plusIcon;
	this.minusIcon = tree.minusIcon;
	this.targetFrame = destFrame;

	if (icon == "" || icon == this.blankIcon) {
		this.isitblank = true;
	} else {
		this.isitblank = false;
	}

	if (tree.imagePath != "") {
		this.icon = tree.imagePath + this.icon;
		this.blankIcon = tree.imagePath + this.blankIcon;
		this.plusIcon =  tree.imagePath + this.plusIcon;
		this.minusIcon =  tree.imagePath + this.minusIcon;
		this.openFolder =  tree.imagePath + this.openFolder;
		this.closedFolder =  tree.imagePath + this.closedFolder;
	}
	nodeIndex[id] = this;

	this.addItem = addItem;
	this.build = build;
	this.buildStatic = buildStatic;
	this.expandChildren = expandChildren;
	this.compressChildren = compressChildren;
	this.getContent = getContent;
	
}

function getContent(){
	var node = this;
	return node.content;
}

/////////////////////////////////////////////////////////////
// addRoot()
//   Set the root node of the tree to be constructed
// Inputs:
//   content - node or foler content (can be HTML text)
//   link - link for the name and icon
//   icon - name of the icon eg. ./file/btn.gif
//   open_icon - name of the icon when the children is expanded
// Output:
//   The tree node that has been created.
/////////////////////////////////////////////////////////////
function addRoot(content, link,destFrame, icon, open_icon) {	
	var selectable = true;
	var localFrame=null;

	if (icon == null || icon == "") {
		icon = this.blankIcon;
	}

	if (open_icon == null || open_icon == "") {
		open_icon = icon;
	}

	if (link == null || link == "") {
		link = "";
		selectable = false;
	} else {
		link = this.linkPath + link;
	}
	if(destFrame==null||destFrame==""){
		localFrame=this.targetFrame;
	}else {
	  localFrame=destFrame;
  }

	var level;
	if (this.showRoot) {
		level = 1;
	} else {
		level = 0;
	}

	var id = this.maxNodes++;
	this.root = new TreeNode(this, content, link,localFrame, icon, open_icon, null, id, level, selectable);
	return this.root;
}

/////////////////////////////////////////////////////////////
// addItem()
//   Add an item to the tree
// Inputs:
//   link - link for the name and icon
//   content - node or folder content (can be HTML text)
//   parent - parent node
//   icon - name of the icon eg. ./file/btn.gif
// Output:
//   The tree node that has been created.
/////////////////////////////////////////////////////////////
function addItem(content, link,destFrame, icon, open_icon) {	
	var tree = this.tree;
	var localFrame=null;

	if (icon == null || icon == "") {
		icon = tree.blankIcon;
	}

	if (open_icon == null || open_icon == "") {
		open_icon = icon;
	}

	var selectable = true;
	if (link == null || link == "") {
		link = "";
		selectable = false;
	} else if (tree.linkPath != "") {
		link = tree.linkPath + link;
		selectable = true;
	}
	link = link.replace(/\s+/g,'');
	

	if(destFrame==null||destFrame==""){
		localFrame=tree.destFrame;
	}else {
	  localFrame=destFrame;
  }

	var id = tree.maxNodes++;
	var tempNode = new TreeNode(tree, content, link,localFrame, icon, open_icon, this, id, this.level+1, selectable);
	this.children[this.childCount++] = tempNode;
	
	return tempNode;
}

/////////////////////////////////////////////////////////////
// expandChildren()
//   Expand the chlidren of the node by making the chlidren
//   nodes visable.
//
// Inputs:
//   depth - the absolute depth to which to expand (optional)
//           null:  expand leaves if previously expanded
//           < 0 :  forcefully expand entire tree
//           > 0 :  forcefully expand tree n levels
// Output:
//   None.
/////////////////////////////////////////////////////////////
function expandChildren(depth) {	
	var node = this;

	if (depth)
		depth--;

	node.expanded = true;
	if (node.childCount > 0) {
		node.button.src = node.MSrc;
		if (!node.isitblank) {
			node.img.src = node.openFolder;
		}
	}
  
	for (var i = 0; i < node.childCount; i++) {
		var cnode = node.children[i];

		document.all["Item"+cnode.id].style.display = "block";
		cnode.visible = true;
		
		if (depth == null && cnode.expanded) {
			cnode.expandChildren();
		} else {
			if (depth != null && depth != 0) {
				cnode.expandChildren(depth);
			}
		}
	}
}


/////////////////////////////////////////////////////////////
// compressChildren()
//   Compress the chlidren of the node by hiding its chlidren
//   nodes.
//
// Inputs:
// 	 None.
// Output:
//   None.
/////////////////////////////////////////////////////////////
function compressChildren() {	
	var node = this;

	if (node.childCount > 0) {
		node.button.src = node.PSrc;
		if (!node.isitblank) {
			node.img.src = node.closedFolder;
		}
	}

	for (var i = 0; i < node.childCount; i++) {
		var cnode = node.children[i];

		document.all["Item"+cnode.id].style.display = "none";
		cnode.visible = false;
		if (cnode.childCount > 0)
			cnode.compressChildren();
	}
}

/////////////////////////////////////////////////////////////
// expandCompressNode()
//   Event Handler for the Plus-Minus buttons.  This procedure
//   will either expand a node or compress a node depending on
//   the current state of node.
//
// Inputs:
//   id - the id number of the node of which created the event
// Output:
//   None.
/////////////////////////////////////////////////////////////
function expandCompressNode(id) {	
	var node = nodeIndex[id];
	  
	if (!node.expanded) {
		node.expandChildren();
	} else {		
		node.expanded = false;
		node.compressChildren();
	}
}

/////////////////////////////////////////////////////////////
// build()
//   display the tree built in memory
//
// Inputs:
//   node - the node to show
// Outputs:
//   The tree is displayed to the browser's window
/////////////////////////////////////////////////////////////
function build() {	
	var node = this;
	
	if (node.childCount > 0) {
		document.write("<div class='indent"+node.level+"kids' id=Item"+node.id+">");
	} else {
		document.write("<div class='indent"+node.level+"' id=Item"+node.id+">"); 
	}        
	if (node.childCount >0) {
		if (node.showExpanders) {
			node.PSrc = node.plusIcon;
			node.MSrc = node.minusIcon;
		} else {
			node.PSrc = node.blankIcon;
			node.MSrc = node.blankIcon;
		}
		
		//添加onclick事件
		document.write("<a onclick='treeOnclick("+node.id+");return false' ");		
		document.write("href='#'>");

		document.write("<img src='"+node.PSrc+"' name='PM"+node.id+"' id='plusminus' ");
		document.write("align='texttop' border='0' alt=''>");
		
		//显示图片
		if (!node.isitblank) {
			document.write("<img src="+node.icon+" name='icon"+node.id+"' align='texttop' border='0'>");
		}

		document.write("</a> ");
		node.button = document.all['PM'+node.id];		
    
		if (node.selectable) {
			document.write("<a target='"+node.targetFrame+"' href=\"" + ebs(node.link) + " \">");      
		} else {
			document.write("<a id='plusminus' onclick='treeOnclick("+node.id+");return false' ");			
			document.write("onfocus=\"treeOnFocus(event)\" ");
			document.write("onblur=\"treeOnBlur(event)\" ");
			document.write("href='#'>");
		}

		document.write(node.content+"</a>");
		document.write("</div>");
		node.img = document.all['icon'+node.id];
    
		for (var i = 0; i < node.childCount; i++) {
			node.children[i].build();
		}
	} else {
		if (!node.isitblank) {
			document.write("<img src="+node.icon+" name='icon" + node.id + "' align='texttop' border='0'>&nbsp;");
			node.img = document.all['icon'+node.id];
		}

		if (node.selectable) {
			document.write("<a target='"+node.targetFrame+"' href=\"" + ebs(node.link) + "\"");
			document.write("onfocus=\"treeOnFocus(event)\" ");
			document.write("onblur=\"treeOnBlur(event)\" ");
			document.write(">");

		}

		document.write(node.content);
		if (node.selectable) {
			document.write("</a>");
		}

		document.write("</div>");
	} 	
	document.all["Item"+node.id].style.display = "none";
}



/////////////////////////////////////////////////////////////
// buildStatic()
//   display the tree built in memory
//
// Inputs:
//   node - the node to show
// Outputs:
//   The tree is displayed to the browser's window
/////////////////////////////////////////////////////////////
function buildStatic() {	
	var node = this;

	if (node.childCount > 0) {
		document.write("<div class='indent"+node.level+"'>");
		document.write("<img src="+node.icon+" name='icon"+node.id+"' align='texttop' border='0'>");
		if (node.selectable) {
			document.write("<a target='"+node.targetFrame+"' href=\"" + ebs(node.link) + " \">");      
			document.write(node.content+"</a>");
		} else {
			document.write(node.content);
		}
		document.write("</div>");

		for (var i = 0; i < node.childCount; i++) {
			node.children[i].buildStatic();
		}
	} else {
		document.write("<div class='indent"+node.level+"'>");
		document.write("<img src="+node.icon+" name='icon"+node.id+"' align='texttop' border='0'> ");
		if (node.selectable) {
			document.write("<a target='"+node.targetFrame+"' href=\"" + ebs(node.link) + "\">");
			document.write(node.content);
			document.write( "</a>" );
		} else {
			document.write(node.content);
		}

		document.write("</div>");
	} 
}

// browsers parse (and unescape) the href in the processing
// of an onclick.  By the time str gets to our callback,
// it is escaped again.  This ensures our callback gets a
// string with escaped backslashes.
function ebs(str) {
	if (!str || str == null)
		return("");

	return(str.replace(/\\/g, "\\\\"));
}

function clk(targetFrame, sPath) {
	targetFrame.location= sPath;
}

/////////////////////////////////////////////////////////////
//   Function use in caller to set some global variable
//   setImagePath: Set the icon image path
//
//   setLinkPath: Set the link path
//
//   setExpandDepth: Set the initial expand depth
//
//   setShowExpanders: Show the plus/minus icon or not
//
//   setTargetFrame: Set the link target
/////////////////////////////////////////////////////////////
function setImagePath(ip) {
	this.imagePath = ip + "/";
}

function setLinkPath (ip) {
	this.linkPath = ip + "/";
}

function setExpandDepth(iDepth) {
	if (iDepth != null)
		this.expandDepth = iDepth;
}

function setShowExpanders(sExpand) {
	this.showExpanders = sExpand;
}

function setTargetFrame(tf) {
	this.targetFrame = tf;
}

function setShowRoot(sr) {
	this.showRoot = sr;
}

function setplusIcon(spi) {
	this.plusIcon = spi;
}

function setminusIcon(mi) {
	this.minusIcon = mi;
}



function treeOnclick(index){		
	//调用expandCompressNode
	expandCompressNode(index);		
	
}

/**
 * toolbar的onfocus事件
 */

function treeOnFocus(e){
	e = window.event || e;
	var node = e.srcElement || e.target;
	node.className = "onFocus";	
	return true;
}

/**
 * toolbar的onblur事件
 */

function treeOnBlur(e){		
	e = window.event || e;
	var node = e.srcElement || e.target;	
	node.className = "onBlur";	
	return false;
}




function trim(txt) {
	if (txt =="" || txt == " ") {
		return "";
	}
	while(true) {
		if (txt.indexOf(" ") == 0) {
			txt = txt.substring(1);
			if (txt == " ") {
				txt = "";
				return txt;
			}
		}
		else {
			break;
		}
	}
	while(true) {
		if (txt.substring(txt.length - 1) == " ") {
			txt = txt.substring(0,txt.length - 1);
			if (txt == " ") {
				txt = "";
				return txt;
			}
		}
		else {
			break;
		}
	}
	return txt;
}
