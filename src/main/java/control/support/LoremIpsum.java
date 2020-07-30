package control.support;

public abstract class LoremIpsum {

	private final static String LOREM_IPSUM_5P = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed blandit condimentum enim in consectetur. Vivamus aliquet laoreet egestas. Nullam imperdiet aliquet porta. Fusce suscipit vulputate leo, eget hendrerit lorem aliquet quis. Praesent rhoncus ex ac felis porta, volutpat auctor metus fermentum. Vestibulum ac ullamcorper nisi, non dictum magna. Aliquam in interdum tortor, lacinia imperdiet sem. Aenean orci velit, venenatis non ante quis, vestibulum luctus libero.\r\n"
			+ "\r\n"
			+ "Vivamus euismod odio id diam blandit tincidunt. Curabitur vitae tortor hendrerit, facilisis odio sit amet, ultrices nisl. Nam sit amet fermentum nisi. Praesent vitae ipsum a libero tincidunt lobortis. Aenean a lacus varius felis rutrum lacinia vel molestie nunc. Sed vel congue tellus. Nunc scelerisque id est ac posuere. Proin eget leo ligula. Aliquam accumsan sed risus vitae pulvinar. Cras rutrum nec nunc ac tempus.\r\n"
			+ "\r\n"
			+ "Etiam mattis elit a odio commodo bibendum. Aenean et consectetur odio, eu ornare odio. Phasellus massa odio, tincidunt ut lacinia quis, consectetur vitae nunc. Morbi feugiat lobortis nisl, at ornare est fringilla ac. Mauris in semper ligula. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Proin scelerisque ex enim, a condimentum odio dictum in. Integer eget magna interdum, ullamcorper mauris sit amet, gravida orci. In tincidunt risus eget magna pretium facilisis id ut arcu. Donec ut elit in turpis commodo dictum. Integer luctus ipsum lacus, sed vehicula lectus vehicula eget. Quisque id lacinia leo. Cras tortor massa, accumsan eu nisl nec, placerat consequat lacus. Sed bibendum turpis et augue finibus laoreet. Cras nunc tellus, rhoncus nec hendrerit nec, varius eu purus. Proin gravida erat nunc, ac mattis tortor volutpat sed.\r\n"
			+ "\r\n"
			+ "Aliquam pellentesque arcu dictum, pulvinar tortor in, pulvinar est. Fusce purus sapien, consectetur vel imperdiet sit amet, blandit in lorem. In nec sodales erat, vel mollis augue. Donec velit nunc, consequat suscipit sodales et, mattis gravida ante. Morbi vestibulum, lectus sit amet laoreet porttitor, lacus lorem blandit magna, at ultricies metus turpis vitae nisi. Aenean condimentum quam et erat egestas viverra. Pellentesque porttitor pharetra ex ut maximus. Nullam vitae ante ut mauris mattis eleifend quis vel libero. Aenean pretium sem sapien, vitae rutrum purus maximus at. Sed a odio augue. Praesent auctor dapibus mattis. Maecenas consequat risus sit amet est euismod porta a sed libero. Cras feugiat, tortor mattis imperdiet pharetra, felis erat rutrum nunc, id laoreet massa urna at odio. Ut condimentum ut nisi ut interdum. Praesent dignissim, turpis a dapibus rhoncus, justo dui posuere augue, et molestie odio neque in lectus. Nunc sem diam, luctus ut neque at, faucibus congue lectus.\r\n"
			+ "\r\n"
			+ "Pellentesque pretium ultrices urna, sed auctor metus vulputate a. Fusce rhoncus dui aliquet orci porttitor, in molestie turpis pretium. Donec ullamcorper neque sed velit tincidunt, quis feugiat leo molestie. Vestibulum sit amet commodo massa, a viverra ligula. Phasellus tempor felis non viverra dapibus. Vivamus tristique dui sed porttitor efficitur. Curabitur vitae lorem et ipsum luctus interdum. Pellentesque nibh leo, pharetra tempus accumsan id, iaculis sed nisi. Suspendisse sollicitudin gravida metus, vitae tristique diam.";
	private static final String LOREM_IPSUM_1P = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc rhoncus faucibus mi. Proin at urna congue, hendrerit nulla at, tincidunt dolor. Mauris eget eros eu erat maximus vestibulum ut vitae tellus. Praesent vestibulum auctor eros. Praesent quam nisi, lacinia eu quam nec, luctus pharetra elit. Cras placerat porttitor sollicitudin. In sit amet dictum metus. Curabitur quam mi, faucibus ac aliquam vel, dapibus sed justo. Etiam ut ligula ac purus lobortis mattis. Fusce sed odio rhoncus, auctor quam at, lobortis nibh. Nunc malesuada sem nec mi dignissim malesuada. Quisque quis libero quam. Proin ipsum leo, elementum nec arcu at, bibendum hendrerit sem.";
	private static final String LOREM_IPSUM_2P = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus eu ornare neque. Donec elementum diam id arcu tempor, ut tempor libero pulvinar. Mauris nec commodo lectus. Aliquam erat volutpat. Nam aliquet magna et sem vulputate mattis. Ut in vulputate felis, nec cursus nisi. Aliquam consequat, felis a semper gravida, neque elit pharetra tortor, ac eleifend erat odio sit amet ex. Aenean velit nisl, accumsan quis consectetur et, bibendum non nibh. In hac habitasse platea dictumst. Etiam sollicitudin metus a bibendum laoreet.\r\n"
			+ "\r\n"
			+ "Donec vel mollis mi, at dignissim nisl. Fusce at ex nisi. Ut at neque nunc. Ut vitae pretium felis, at varius enim. Duis elementum, risus id scelerisque porta, leo nisi tempor felis, vel varius libero massa in velit. Pellentesque efficitur massa elit, eu suscipit quam varius semper. Donec mollis dignissim maximus. Maecenas iaculis molestie sollicitudin.";

	public static String getLoremIpsum5p() {
		return LOREM_IPSUM_5P;
	}

	public static String getLoremIpsum1p() {
		return LOREM_IPSUM_1P;
	}

	public static String getLoremIpsum2p() {
		return LOREM_IPSUM_2P;
	}
}
