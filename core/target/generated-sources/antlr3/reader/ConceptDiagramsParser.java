// $ANTLR 3.5.1 ConceptDiagrams.g 2018-01-08 19:03:27

package speedith.core.lang.reader;
import static speedith.core.i18n.Translations.i18n;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

import org.antlr.runtime.tree.*;


@SuppressWarnings("all")
public class ConceptDiagramsParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "CD", "COMMENT", "DICT", "ESC_SEQ", 
		"HEX_DIGIT", "ID", "IdentifierPart", "IdentifierStart", "LIST", "OCTAL_ESC", 
		"PAIR", "SLIST", "STRING", "UNICODE_ESC", "WS", "')'", "','", "'CD'", 
		"']'", "'}'"
	};
	public static final int EOF=-1;
	public static final int T__19=19;
	public static final int T__20=20;
	public static final int T__21=21;
	public static final int T__22=22;
	public static final int T__23=23;
	public static final int CD=4;
	public static final int COMMENT=5;
	public static final int DICT=6;
	public static final int ESC_SEQ=7;
	public static final int HEX_DIGIT=8;
	public static final int ID=9;
	public static final int IdentifierPart=10;
	public static final int IdentifierStart=11;
	public static final int LIST=12;
	public static final int OCTAL_ESC=13;
	public static final int PAIR=14;
	public static final int SLIST=15;
	public static final int STRING=16;
	public static final int UNICODE_ESC=17;
	public static final int WS=18;

	// delegates
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public ConceptDiagramsParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public ConceptDiagramsParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	protected TreeAdaptor adaptor = new CommonTreeAdaptor();

	public void setTreeAdaptor(TreeAdaptor adaptor) {
		this.adaptor = adaptor;
	}
	public TreeAdaptor getTreeAdaptor() {
		return adaptor;
	}
	@Override public String[] getTokenNames() { return ConceptDiagramsParser.tokenNames; }
	@Override public String getGrammarFileName() { return "ConceptDiagrams.g"; }


	@Override
	public void reportError(RecognitionException e) {
	    throw new ParseException(i18n("ERR_PARSE_INVALID_SYNTAX"), e, this);
	}


	public static class start_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "start"
	// ConceptDiagrams.g:59:1: start : conceptDiagram ;
	public final ConceptDiagramsParser.start_return start() throws RecognitionException {
		ConceptDiagramsParser.start_return retval = new ConceptDiagramsParser.start_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		ParserRuleReturnScope conceptDiagram1 =null;


		try {
			// ConceptDiagrams.g:60:5: ( conceptDiagram )
			// ConceptDiagrams.g:60:10: conceptDiagram
			{
			root_0 = (CommonTree)adaptor.nil();


			pushFollow(FOLLOW_conceptDiagram_in_start254);
			conceptDiagram1=conceptDiagram();
			state._fsp--;

			adaptor.addChild(root_0, conceptDiagram1.getTree());

			}

			retval.stop = input.LT(-1);

			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}

		catch (RecognitionException e) {
		    throw new ParseException(i18n("ERR_PARSE_INVALID_SYNTAX"), e, this);
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "start"


	public static class conceptDiagram_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "conceptDiagram"
	// ConceptDiagrams.g:63:1: conceptDiagram : 'CD' ^ '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !;
	public final ConceptDiagramsParser.conceptDiagram_return conceptDiagram() throws RecognitionException {
		ConceptDiagramsParser.conceptDiagram_return retval = new ConceptDiagramsParser.conceptDiagram_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		Token string_literal2=null;
		Token char_literal3=null;
		Token char_literal5=null;
		Token char_literal7=null;
		ParserRuleReturnScope keyValue4 =null;
		ParserRuleReturnScope keyValue6 =null;

		CommonTree string_literal2_tree=null;
		CommonTree char_literal3_tree=null;
		CommonTree char_literal5_tree=null;
		CommonTree char_literal7_tree=null;

		try {
			// ConceptDiagrams.g:64:5: ( 'CD' ^ '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !)
			// ConceptDiagrams.g:64:10: 'CD' ^ '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !
			{
			root_0 = (CommonTree)adaptor.nil();


			string_literal2=(Token)match(input,21,FOLLOW_21_in_conceptDiagram274); 
			string_literal2_tree = (CommonTree)adaptor.create(string_literal2);
			root_0 = (CommonTree)adaptor.becomeRoot(string_literal2_tree, root_0);

			char_literal3=(Token)match(input,DICT,FOLLOW_DICT_in_conceptDiagram277); 
			// ConceptDiagrams.g:64:21: ( keyValue ( ',' ! keyValue )* )?
			int alt2=2;
			int LA2_0 = input.LA(1);
			if ( (LA2_0==ID) ) {
				alt2=1;
			}
			switch (alt2) {
				case 1 :
					// ConceptDiagrams.g:64:22: keyValue ( ',' ! keyValue )*
					{
					pushFollow(FOLLOW_keyValue_in_conceptDiagram281);
					keyValue4=keyValue();
					state._fsp--;

					adaptor.addChild(root_0, keyValue4.getTree());

					// ConceptDiagrams.g:64:31: ( ',' ! keyValue )*
					loop1:
					while (true) {
						int alt1=2;
						int LA1_0 = input.LA(1);
						if ( (LA1_0==20) ) {
							alt1=1;
						}

						switch (alt1) {
						case 1 :
							// ConceptDiagrams.g:64:32: ',' ! keyValue
							{
							char_literal5=(Token)match(input,20,FOLLOW_20_in_conceptDiagram284); 
							pushFollow(FOLLOW_keyValue_in_conceptDiagram287);
							keyValue6=keyValue();
							state._fsp--;

							adaptor.addChild(root_0, keyValue6.getTree());

							}
							break;

						default :
							break loop1;
						}
					}

					}
					break;

			}

			char_literal7=(Token)match(input,23,FOLLOW_23_in_conceptDiagram293); 
			}

			retval.stop = input.LT(-1);

			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}

		catch (RecognitionException e) {
		    throw new ParseException(i18n("ERR_PARSE_INVALID_SYNTAX"), e, this);
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "conceptDiagram"


	public static class keyValues_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "keyValues"
	// ConceptDiagrams.g:67:1: keyValues : '{' ^ ( keyValue ( ',' ! keyValue )* )? '}' !;
	public final ConceptDiagramsParser.keyValues_return keyValues() throws RecognitionException {
		ConceptDiagramsParser.keyValues_return retval = new ConceptDiagramsParser.keyValues_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		Token char_literal8=null;
		Token char_literal10=null;
		Token char_literal12=null;
		ParserRuleReturnScope keyValue9 =null;
		ParserRuleReturnScope keyValue11 =null;

		CommonTree char_literal8_tree=null;
		CommonTree char_literal10_tree=null;
		CommonTree char_literal12_tree=null;

		try {
			// ConceptDiagrams.g:68:5: ( '{' ^ ( keyValue ( ',' ! keyValue )* )? '}' !)
			// ConceptDiagrams.g:68:10: '{' ^ ( keyValue ( ',' ! keyValue )* )? '}' !
			{
			root_0 = (CommonTree)adaptor.nil();


			char_literal8=(Token)match(input,DICT,FOLLOW_DICT_in_keyValues314); 
			char_literal8_tree = (CommonTree)adaptor.create(char_literal8);
			root_0 = (CommonTree)adaptor.becomeRoot(char_literal8_tree, root_0);

			// ConceptDiagrams.g:68:15: ( keyValue ( ',' ! keyValue )* )?
			int alt4=2;
			int LA4_0 = input.LA(1);
			if ( (LA4_0==ID) ) {
				alt4=1;
			}
			switch (alt4) {
				case 1 :
					// ConceptDiagrams.g:68:16: keyValue ( ',' ! keyValue )*
					{
					pushFollow(FOLLOW_keyValue_in_keyValues318);
					keyValue9=keyValue();
					state._fsp--;

					adaptor.addChild(root_0, keyValue9.getTree());

					// ConceptDiagrams.g:68:25: ( ',' ! keyValue )*
					loop3:
					while (true) {
						int alt3=2;
						int LA3_0 = input.LA(1);
						if ( (LA3_0==20) ) {
							alt3=1;
						}

						switch (alt3) {
						case 1 :
							// ConceptDiagrams.g:68:26: ',' ! keyValue
							{
							char_literal10=(Token)match(input,20,FOLLOW_20_in_keyValues321); 
							pushFollow(FOLLOW_keyValue_in_keyValues324);
							keyValue11=keyValue();
							state._fsp--;

							adaptor.addChild(root_0, keyValue11.getTree());

							}
							break;

						default :
							break loop3;
						}
					}

					}
					break;

			}

			char_literal12=(Token)match(input,23,FOLLOW_23_in_keyValues330); 
			}

			retval.stop = input.LT(-1);

			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}

		catch (RecognitionException e) {
		    throw new ParseException(i18n("ERR_PARSE_INVALID_SYNTAX"), e, this);
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "keyValues"


	public static class list_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "list"
	// ConceptDiagrams.g:71:1: list : '[' ^ ( languageElement ( ',' ! languageElement )* )? ']' !;
	public final ConceptDiagramsParser.list_return list() throws RecognitionException {
		ConceptDiagramsParser.list_return retval = new ConceptDiagramsParser.list_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		Token char_literal13=null;
		Token char_literal15=null;
		Token char_literal17=null;
		ParserRuleReturnScope languageElement14 =null;
		ParserRuleReturnScope languageElement16 =null;

		CommonTree char_literal13_tree=null;
		CommonTree char_literal15_tree=null;
		CommonTree char_literal17_tree=null;

		try {
			// ConceptDiagrams.g:72:5: ( '[' ^ ( languageElement ( ',' ! languageElement )* )? ']' !)
			// ConceptDiagrams.g:72:10: '[' ^ ( languageElement ( ',' ! languageElement )* )? ']' !
			{
			root_0 = (CommonTree)adaptor.nil();


			char_literal13=(Token)match(input,LIST,FOLLOW_LIST_in_list351); 
			char_literal13_tree = (CommonTree)adaptor.create(char_literal13);
			root_0 = (CommonTree)adaptor.becomeRoot(char_literal13_tree, root_0);

			// ConceptDiagrams.g:72:15: ( languageElement ( ',' ! languageElement )* )?
			int alt6=2;
			int LA6_0 = input.LA(1);
			if ( (LA6_0==DICT||LA6_0==LIST||(LA6_0 >= SLIST && LA6_0 <= STRING)||LA6_0==21) ) {
				alt6=1;
			}
			switch (alt6) {
				case 1 :
					// ConceptDiagrams.g:72:16: languageElement ( ',' ! languageElement )*
					{
					pushFollow(FOLLOW_languageElement_in_list355);
					languageElement14=languageElement();
					state._fsp--;

					adaptor.addChild(root_0, languageElement14.getTree());

					// ConceptDiagrams.g:72:32: ( ',' ! languageElement )*
					loop5:
					while (true) {
						int alt5=2;
						int LA5_0 = input.LA(1);
						if ( (LA5_0==20) ) {
							alt5=1;
						}

						switch (alt5) {
						case 1 :
							// ConceptDiagrams.g:72:33: ',' ! languageElement
							{
							char_literal15=(Token)match(input,20,FOLLOW_20_in_list358); 
							pushFollow(FOLLOW_languageElement_in_list361);
							languageElement16=languageElement();
							state._fsp--;

							adaptor.addChild(root_0, languageElement16.getTree());

							}
							break;

						default :
							break loop5;
						}
					}

					}
					break;

			}

			char_literal17=(Token)match(input,22,FOLLOW_22_in_list367); 
			}

			retval.stop = input.LT(-1);

			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}

		catch (RecognitionException e) {
		    throw new ParseException(i18n("ERR_PARSE_INVALID_SYNTAX"), e, this);
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "list"


	public static class sortedList_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "sortedList"
	// ConceptDiagrams.g:75:1: sortedList : '(' ^ ( languageElement ( ',' ! languageElement )* )? ')' !;
	public final ConceptDiagramsParser.sortedList_return sortedList() throws RecognitionException {
		ConceptDiagramsParser.sortedList_return retval = new ConceptDiagramsParser.sortedList_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		Token char_literal18=null;
		Token char_literal20=null;
		Token char_literal22=null;
		ParserRuleReturnScope languageElement19 =null;
		ParserRuleReturnScope languageElement21 =null;

		CommonTree char_literal18_tree=null;
		CommonTree char_literal20_tree=null;
		CommonTree char_literal22_tree=null;

		try {
			// ConceptDiagrams.g:76:5: ( '(' ^ ( languageElement ( ',' ! languageElement )* )? ')' !)
			// ConceptDiagrams.g:76:10: '(' ^ ( languageElement ( ',' ! languageElement )* )? ')' !
			{
			root_0 = (CommonTree)adaptor.nil();


			char_literal18=(Token)match(input,SLIST,FOLLOW_SLIST_in_sortedList388); 
			char_literal18_tree = (CommonTree)adaptor.create(char_literal18);
			root_0 = (CommonTree)adaptor.becomeRoot(char_literal18_tree, root_0);

			// ConceptDiagrams.g:76:15: ( languageElement ( ',' ! languageElement )* )?
			int alt8=2;
			int LA8_0 = input.LA(1);
			if ( (LA8_0==DICT||LA8_0==LIST||(LA8_0 >= SLIST && LA8_0 <= STRING)||LA8_0==21) ) {
				alt8=1;
			}
			switch (alt8) {
				case 1 :
					// ConceptDiagrams.g:76:16: languageElement ( ',' ! languageElement )*
					{
					pushFollow(FOLLOW_languageElement_in_sortedList392);
					languageElement19=languageElement();
					state._fsp--;

					adaptor.addChild(root_0, languageElement19.getTree());

					// ConceptDiagrams.g:76:32: ( ',' ! languageElement )*
					loop7:
					while (true) {
						int alt7=2;
						int LA7_0 = input.LA(1);
						if ( (LA7_0==20) ) {
							alt7=1;
						}

						switch (alt7) {
						case 1 :
							// ConceptDiagrams.g:76:33: ',' ! languageElement
							{
							char_literal20=(Token)match(input,20,FOLLOW_20_in_sortedList395); 
							pushFollow(FOLLOW_languageElement_in_sortedList398);
							languageElement21=languageElement();
							state._fsp--;

							adaptor.addChild(root_0, languageElement21.getTree());

							}
							break;

						default :
							break loop7;
						}
					}

					}
					break;

			}

			char_literal22=(Token)match(input,19,FOLLOW_19_in_sortedList404); 
			}

			retval.stop = input.LT(-1);

			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}

		catch (RecognitionException e) {
		    throw new ParseException(i18n("ERR_PARSE_INVALID_SYNTAX"), e, this);
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "sortedList"


	public static class keyValue_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "keyValue"
	// ConceptDiagrams.g:79:1: keyValue : ID '=' ^ languageElement ;
	public final ConceptDiagramsParser.keyValue_return keyValue() throws RecognitionException {
		ConceptDiagramsParser.keyValue_return retval = new ConceptDiagramsParser.keyValue_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		Token ID23=null;
		Token char_literal24=null;
		ParserRuleReturnScope languageElement25 =null;

		CommonTree ID23_tree=null;
		CommonTree char_literal24_tree=null;

		try {
			// ConceptDiagrams.g:80:5: ( ID '=' ^ languageElement )
			// ConceptDiagrams.g:80:10: ID '=' ^ languageElement
			{
			root_0 = (CommonTree)adaptor.nil();


			ID23=(Token)match(input,ID,FOLLOW_ID_in_keyValue425); 
			ID23_tree = (CommonTree)adaptor.create(ID23);
			adaptor.addChild(root_0, ID23_tree);

			char_literal24=(Token)match(input,PAIR,FOLLOW_PAIR_in_keyValue427); 
			char_literal24_tree = (CommonTree)adaptor.create(char_literal24);
			root_0 = (CommonTree)adaptor.becomeRoot(char_literal24_tree, root_0);

			pushFollow(FOLLOW_languageElement_in_keyValue430);
			languageElement25=languageElement();
			state._fsp--;

			adaptor.addChild(root_0, languageElement25.getTree());

			}

			retval.stop = input.LT(-1);

			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}

		catch (RecognitionException e) {
		    throw new ParseException(i18n("ERR_PARSE_INVALID_SYNTAX"), e, this);
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "keyValue"


	public static class languageElement_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "languageElement"
	// ConceptDiagrams.g:83:1: languageElement : ( STRING | keyValues | list | sortedList | conceptDiagram );
	public final ConceptDiagramsParser.languageElement_return languageElement() throws RecognitionException {
		ConceptDiagramsParser.languageElement_return retval = new ConceptDiagramsParser.languageElement_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		Token STRING26=null;
		ParserRuleReturnScope keyValues27 =null;
		ParserRuleReturnScope list28 =null;
		ParserRuleReturnScope sortedList29 =null;
		ParserRuleReturnScope conceptDiagram30 =null;

		CommonTree STRING26_tree=null;

		try {
			// ConceptDiagrams.g:84:5: ( STRING | keyValues | list | sortedList | conceptDiagram )
			int alt9=5;
			switch ( input.LA(1) ) {
			case STRING:
				{
				alt9=1;
				}
				break;
			case DICT:
				{
				alt9=2;
				}
				break;
			case LIST:
				{
				alt9=3;
				}
				break;
			case SLIST:
				{
				alt9=4;
				}
				break;
			case 21:
				{
				alt9=5;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 9, 0, input);
				throw nvae;
			}
			switch (alt9) {
				case 1 :
					// ConceptDiagrams.g:84:10: STRING
					{
					root_0 = (CommonTree)adaptor.nil();


					STRING26=(Token)match(input,STRING,FOLLOW_STRING_in_languageElement450); 
					STRING26_tree = (CommonTree)adaptor.create(STRING26);
					adaptor.addChild(root_0, STRING26_tree);

					}
					break;
				case 2 :
					// ConceptDiagrams.g:85:10: keyValues
					{
					root_0 = (CommonTree)adaptor.nil();


					pushFollow(FOLLOW_keyValues_in_languageElement461);
					keyValues27=keyValues();
					state._fsp--;

					adaptor.addChild(root_0, keyValues27.getTree());

					}
					break;
				case 3 :
					// ConceptDiagrams.g:86:10: list
					{
					root_0 = (CommonTree)adaptor.nil();


					pushFollow(FOLLOW_list_in_languageElement472);
					list28=list();
					state._fsp--;

					adaptor.addChild(root_0, list28.getTree());

					}
					break;
				case 4 :
					// ConceptDiagrams.g:87:10: sortedList
					{
					root_0 = (CommonTree)adaptor.nil();


					pushFollow(FOLLOW_sortedList_in_languageElement483);
					sortedList29=sortedList();
					state._fsp--;

					adaptor.addChild(root_0, sortedList29.getTree());

					}
					break;
				case 5 :
					// ConceptDiagrams.g:88:10: conceptDiagram
					{
					root_0 = (CommonTree)adaptor.nil();


					pushFollow(FOLLOW_conceptDiagram_in_languageElement494);
					conceptDiagram30=conceptDiagram();
					state._fsp--;

					adaptor.addChild(root_0, conceptDiagram30.getTree());

					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}

		catch (RecognitionException e) {
		    throw new ParseException(i18n("ERR_PARSE_INVALID_SYNTAX"), e, this);
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "languageElement"

	// Delegated rules



	public static final BitSet FOLLOW_conceptDiagram_in_start254 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_21_in_conceptDiagram274 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_DICT_in_conceptDiagram277 = new BitSet(new long[]{0x0000000000800200L});
	public static final BitSet FOLLOW_keyValue_in_conceptDiagram281 = new BitSet(new long[]{0x0000000000900000L});
	public static final BitSet FOLLOW_20_in_conceptDiagram284 = new BitSet(new long[]{0x0000000000000200L});
	public static final BitSet FOLLOW_keyValue_in_conceptDiagram287 = new BitSet(new long[]{0x0000000000900000L});
	public static final BitSet FOLLOW_23_in_conceptDiagram293 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_DICT_in_keyValues314 = new BitSet(new long[]{0x0000000000800200L});
	public static final BitSet FOLLOW_keyValue_in_keyValues318 = new BitSet(new long[]{0x0000000000900000L});
	public static final BitSet FOLLOW_20_in_keyValues321 = new BitSet(new long[]{0x0000000000000200L});
	public static final BitSet FOLLOW_keyValue_in_keyValues324 = new BitSet(new long[]{0x0000000000900000L});
	public static final BitSet FOLLOW_23_in_keyValues330 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LIST_in_list351 = new BitSet(new long[]{0x0000000000619040L});
	public static final BitSet FOLLOW_languageElement_in_list355 = new BitSet(new long[]{0x0000000000500000L});
	public static final BitSet FOLLOW_20_in_list358 = new BitSet(new long[]{0x0000000000219040L});
	public static final BitSet FOLLOW_languageElement_in_list361 = new BitSet(new long[]{0x0000000000500000L});
	public static final BitSet FOLLOW_22_in_list367 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_SLIST_in_sortedList388 = new BitSet(new long[]{0x0000000000299040L});
	public static final BitSet FOLLOW_languageElement_in_sortedList392 = new BitSet(new long[]{0x0000000000180000L});
	public static final BitSet FOLLOW_20_in_sortedList395 = new BitSet(new long[]{0x0000000000219040L});
	public static final BitSet FOLLOW_languageElement_in_sortedList398 = new BitSet(new long[]{0x0000000000180000L});
	public static final BitSet FOLLOW_19_in_sortedList404 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_keyValue425 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_PAIR_in_keyValue427 = new BitSet(new long[]{0x0000000000219040L});
	public static final BitSet FOLLOW_languageElement_in_keyValue430 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_STRING_in_languageElement450 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_keyValues_in_languageElement461 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_list_in_languageElement472 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_sortedList_in_languageElement483 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_conceptDiagram_in_languageElement494 = new BitSet(new long[]{0x0000000000000002L});
}
