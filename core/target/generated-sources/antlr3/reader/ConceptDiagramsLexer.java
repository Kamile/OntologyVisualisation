// $ANTLR 3.5.1 ConceptDiagrams.g 2018-01-08 19:03:27

package speedith.core.lang.reader;
import static speedith.core.i18n.Translations.i18n;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class ConceptDiagramsLexer extends Lexer {
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

	@Override
	public void reportError(RecognitionException e) {
	    throw new ParseException(i18n("ERR_PARSE_INVALID_SYNTAX"), e, this);
	}


	// delegates
	// delegators
	public Lexer[] getDelegates() {
		return new Lexer[] {};
	}

	public ConceptDiagramsLexer() {} 
	public ConceptDiagramsLexer(CharStream input) {
		this(input, new RecognizerSharedState());
	}
	public ConceptDiagramsLexer(CharStream input, RecognizerSharedState state) {
		super(input,state);
	}
	@Override public String getGrammarFileName() { return "ConceptDiagrams.g"; }

	// $ANTLR start "CD"
	public final void mCD() throws RecognitionException {
		try {
			int _type = CD;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ConceptDiagrams.g:18:4: ( 'ConceptDiagram' )
			// ConceptDiagrams.g:18:6: 'ConceptDiagram'
			{
			match("ConceptDiagram"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "CD"

	// $ANTLR start "DICT"
	public final void mDICT() throws RecognitionException {
		try {
			int _type = DICT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ConceptDiagrams.g:19:6: ( '{' )
			// ConceptDiagrams.g:19:8: '{'
			{
			match('{'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "DICT"

	// $ANTLR start "LIST"
	public final void mLIST() throws RecognitionException {
		try {
			int _type = LIST;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ConceptDiagrams.g:20:6: ( '[' )
			// ConceptDiagrams.g:20:8: '['
			{
			match('['); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "LIST"

	// $ANTLR start "PAIR"
	public final void mPAIR() throws RecognitionException {
		try {
			int _type = PAIR;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ConceptDiagrams.g:21:6: ( '=' )
			// ConceptDiagrams.g:21:8: '='
			{
			match('='); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "PAIR"

	// $ANTLR start "SLIST"
	public final void mSLIST() throws RecognitionException {
		try {
			int _type = SLIST;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ConceptDiagrams.g:22:7: ( '(' )
			// ConceptDiagrams.g:22:9: '('
			{
			match('('); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "SLIST"

	// $ANTLR start "T__19"
	public final void mT__19() throws RecognitionException {
		try {
			int _type = T__19;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ConceptDiagrams.g:23:7: ( ')' )
			// ConceptDiagrams.g:23:9: ')'
			{
			match(')'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__19"

	// $ANTLR start "T__20"
	public final void mT__20() throws RecognitionException {
		try {
			int _type = T__20;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ConceptDiagrams.g:24:7: ( ',' )
			// ConceptDiagrams.g:24:9: ','
			{
			match(','); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__20"

	// $ANTLR start "T__21"
	public final void mT__21() throws RecognitionException {
		try {
			int _type = T__21;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ConceptDiagrams.g:25:7: ( 'CD' )
			// ConceptDiagrams.g:25:9: 'CD'
			{
			match("CD"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__21"

	// $ANTLR start "T__22"
	public final void mT__22() throws RecognitionException {
		try {
			int _type = T__22;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ConceptDiagrams.g:26:7: ( ']' )
			// ConceptDiagrams.g:26:9: ']'
			{
			match(']'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__22"

	// $ANTLR start "T__23"
	public final void mT__23() throws RecognitionException {
		try {
			int _type = T__23;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ConceptDiagrams.g:27:7: ( '}' )
			// ConceptDiagrams.g:27:9: '}'
			{
			match('}'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__23"

	// $ANTLR start "COMMENT"
	public final void mCOMMENT() throws RecognitionException {
		try {
			int _type = COMMENT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ConceptDiagrams.g:102:5: ( '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n' | '/*' ( options {greedy=false; } : . )* '*/' )
			int alt4=2;
			int LA4_0 = input.LA(1);
			if ( (LA4_0=='/') ) {
				int LA4_1 = input.LA(2);
				if ( (LA4_1=='/') ) {
					alt4=1;
				}
				else if ( (LA4_1=='*') ) {
					alt4=2;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 4, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 4, 0, input);
				throw nvae;
			}

			switch (alt4) {
				case 1 :
					// ConceptDiagrams.g:102:9: '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n'
					{
					match("//"); 

					// ConceptDiagrams.g:102:14: (~ ( '\\n' | '\\r' ) )*
					loop1:
					while (true) {
						int alt1=2;
						int LA1_0 = input.LA(1);
						if ( ((LA1_0 >= '\u0000' && LA1_0 <= '\t')||(LA1_0 >= '\u000B' && LA1_0 <= '\f')||(LA1_0 >= '\u000E' && LA1_0 <= '\uFFFF')) ) {
							alt1=1;
						}

						switch (alt1) {
						case 1 :
							// ConceptDiagrams.g:
							{
							if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\t')||(input.LA(1) >= '\u000B' && input.LA(1) <= '\f')||(input.LA(1) >= '\u000E' && input.LA(1) <= '\uFFFF') ) {
								input.consume();
							}
							else {
								MismatchedSetException mse = new MismatchedSetException(null,input);
								recover(mse);
								throw mse;
							}
							}
							break;

						default :
							break loop1;
						}
					}

					// ConceptDiagrams.g:102:28: ( '\\r' )?
					int alt2=2;
					int LA2_0 = input.LA(1);
					if ( (LA2_0=='\r') ) {
						alt2=1;
					}
					switch (alt2) {
						case 1 :
							// ConceptDiagrams.g:102:28: '\\r'
							{
							match('\r'); 
							}
							break;

					}

					match('\n'); 
					_channel=HIDDEN;
					}
					break;
				case 2 :
					// ConceptDiagrams.g:103:9: '/*' ( options {greedy=false; } : . )* '*/'
					{
					match("/*"); 

					// ConceptDiagrams.g:103:14: ( options {greedy=false; } : . )*
					loop3:
					while (true) {
						int alt3=2;
						int LA3_0 = input.LA(1);
						if ( (LA3_0=='*') ) {
							int LA3_1 = input.LA(2);
							if ( (LA3_1=='/') ) {
								alt3=2;
							}
							else if ( ((LA3_1 >= '\u0000' && LA3_1 <= '.')||(LA3_1 >= '0' && LA3_1 <= '\uFFFF')) ) {
								alt3=1;
							}

						}
						else if ( ((LA3_0 >= '\u0000' && LA3_0 <= ')')||(LA3_0 >= '+' && LA3_0 <= '\uFFFF')) ) {
							alt3=1;
						}

						switch (alt3) {
						case 1 :
							// ConceptDiagrams.g:103:42: .
							{
							matchAny(); 
							}
							break;

						default :
							break loop3;
						}
					}

					match("*/"); 

					_channel=HIDDEN;
					}
					break;

			}
			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "COMMENT"

	// $ANTLR start "WS"
	public final void mWS() throws RecognitionException {
		try {
			int _type = WS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ConceptDiagrams.g:102:5: ( ( ' ' | '\\t' | '\\r' | '\\n' ) )
			// ConceptDiagrams.g:102:9: ( ' ' | '\\t' | '\\r' | '\\n' )
			{
			if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			_channel=HIDDEN;
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "WS"

	// $ANTLR start "STRING"
	public final void mSTRING() throws RecognitionException {
		try {
			int _type = STRING;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ConceptDiagrams.g:110:5: ( '\"' ( ESC_SEQ |~ ( '\\\\' | '\"' ) )* '\"' )
			// ConceptDiagrams.g:110:10: '\"' ( ESC_SEQ |~ ( '\\\\' | '\"' ) )* '\"'
			{
			match('\"'); 
			// ConceptDiagrams.g:110:14: ( ESC_SEQ |~ ( '\\\\' | '\"' ) )*
			loop5:
			while (true) {
				int alt5=3;
				int LA5_0 = input.LA(1);
				if ( (LA5_0=='\\') ) {
					alt5=1;
				}
				else if ( ((LA5_0 >= '\u0000' && LA5_0 <= '!')||(LA5_0 >= '#' && LA5_0 <= '[')||(LA5_0 >= ']' && LA5_0 <= '\uFFFF')) ) {
					alt5=2;
				}

				switch (alt5) {
				case 1 :
					// ConceptDiagrams.g:110:16: ESC_SEQ
					{
					mESC_SEQ(); 

					}
					break;
				case 2 :
					// ConceptDiagrams.g:110:26: ~ ( '\\\\' | '\"' )
					{
					if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '!')||(input.LA(1) >= '#' && input.LA(1) <= '[')||(input.LA(1) >= ']' && input.LA(1) <= '\uFFFF') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					break loop5;
				}
			}

			match('\"'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "STRING"

	// $ANTLR start "HEX_DIGIT"
	public final void mHEX_DIGIT() throws RecognitionException {
		try {
			// ConceptDiagrams.g:116:5: ( ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' ) )
			// ConceptDiagrams.g:
			{
			if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'F')||(input.LA(1) >= 'a' && input.LA(1) <= 'f') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "HEX_DIGIT"

	// $ANTLR start "ESC_SEQ"
	public final void mESC_SEQ() throws RecognitionException {
		try {
			// ConceptDiagrams.g:121:5: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' ) | UNICODE_ESC | OCTAL_ESC )
			int alt6=3;
			int LA6_0 = input.LA(1);
			if ( (LA6_0=='\\') ) {
				switch ( input.LA(2) ) {
				case '\"':
				case '\'':
				case '\\':
				case 'b':
				case 'f':
				case 'n':
				case 'r':
				case 't':
					{
					alt6=1;
					}
					break;
				case 'u':
					{
					alt6=2;
					}
					break;
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
					{
					alt6=3;
					}
					break;
				default:
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 6, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 6, 0, input);
				throw nvae;
			}

			switch (alt6) {
				case 1 :
					// ConceptDiagrams.g:121:10: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' )
					{
					match('\\'); 
					if ( input.LA(1)=='\"'||input.LA(1)=='\''||input.LA(1)=='\\'||input.LA(1)=='b'||input.LA(1)=='f'||input.LA(1)=='n'||input.LA(1)=='r'||input.LA(1)=='t' ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;
				case 2 :
					// ConceptDiagrams.g:122:10: UNICODE_ESC
					{
					mUNICODE_ESC(); 

					}
					break;
				case 3 :
					// ConceptDiagrams.g:123:10: OCTAL_ESC
					{
					mOCTAL_ESC(); 

					}
					break;

			}
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ESC_SEQ"

	// $ANTLR start "OCTAL_ESC"
	public final void mOCTAL_ESC() throws RecognitionException {
		try {
			// ConceptDiagrams.g:128:5: ( '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) )
			int alt7=3;
			int LA7_0 = input.LA(1);
			if ( (LA7_0=='\\') ) {
				int LA7_1 = input.LA(2);
				if ( ((LA7_1 >= '0' && LA7_1 <= '3')) ) {
					int LA7_2 = input.LA(3);
					if ( ((LA7_2 >= '0' && LA7_2 <= '7')) ) {
						int LA7_4 = input.LA(4);
						if ( ((LA7_4 >= '0' && LA7_4 <= '7')) ) {
							alt7=1;
						}

						else {
							alt7=2;
						}

					}

					else {
						alt7=3;
					}

				}
				else if ( ((LA7_1 >= '4' && LA7_1 <= '7')) ) {
					int LA7_3 = input.LA(3);
					if ( ((LA7_3 >= '0' && LA7_3 <= '7')) ) {
						alt7=2;
					}

					else {
						alt7=3;
					}

				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 7, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 7, 0, input);
				throw nvae;
			}

			switch (alt7) {
				case 1 :
					// ConceptDiagrams.g:128:10: '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' )
					{
					match('\\'); 
					if ( (input.LA(1) >= '0' && input.LA(1) <= '3') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;
				case 2 :
					// ConceptDiagrams.g:129:10: '\\\\' ( '0' .. '7' ) ( '0' .. '7' )
					{
					match('\\'); 
					if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;
				case 3 :
					// ConceptDiagrams.g:130:10: '\\\\' ( '0' .. '7' )
					{
					match('\\'); 
					if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

			}
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "OCTAL_ESC"

	// $ANTLR start "UNICODE_ESC"
	public final void mUNICODE_ESC() throws RecognitionException {
		try {
			// ConceptDiagrams.g:135:5: ( '\\\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT )
			// ConceptDiagrams.g:135:10: '\\\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
			{
			match('\\'); 
			match('u'); 
			mHEX_DIGIT(); 

			mHEX_DIGIT(); 

			mHEX_DIGIT(); 

			mHEX_DIGIT(); 

			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "UNICODE_ESC"

	// $ANTLR start "ID"
	public final void mID() throws RecognitionException {
		try {
			int _type = ID;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ConceptDiagrams.g:138:5: ( IdentifierStart ( IdentifierPart )* )
			// ConceptDiagrams.g:138:10: IdentifierStart ( IdentifierPart )*
			{
			mIdentifierStart(); 

			// ConceptDiagrams.g:138:26: ( IdentifierPart )*
			loop8:
			while (true) {
				int alt8=2;
				int LA8_0 = input.LA(1);
				if ( ((LA8_0 >= '\u0000' && LA8_0 <= '\b')||(LA8_0 >= '\u000E' && LA8_0 <= '\u001B')||LA8_0=='$'||(LA8_0 >= '0' && LA8_0 <= '9')||(LA8_0 >= 'A' && LA8_0 <= 'Z')||LA8_0=='_'||(LA8_0 >= 'a' && LA8_0 <= 'z')||(LA8_0 >= '\u007F' && LA8_0 <= '\u009F')||(LA8_0 >= '\u00A2' && LA8_0 <= '\u00A5')||LA8_0=='\u00AA'||LA8_0=='\u00AD'||LA8_0=='\u00B5'||LA8_0=='\u00BA'||(LA8_0 >= '\u00C0' && LA8_0 <= '\u00D6')||(LA8_0 >= '\u00D8' && LA8_0 <= '\uDFFF')) ) {
					alt8=1;
				}

				switch (alt8) {
				case 1 :
					// ConceptDiagrams.g:
					{
					if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\b')||(input.LA(1) >= '\u000E' && input.LA(1) <= '\u001B')||input.LA(1)=='$'||(input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z')||(input.LA(1) >= '\u007F' && input.LA(1) <= '\u009F')||(input.LA(1) >= '\u00A2' && input.LA(1) <= '\u00A5')||input.LA(1)=='\u00AA'||input.LA(1)=='\u00AD'||input.LA(1)=='\u00B5'||input.LA(1)=='\u00BA'||(input.LA(1) >= '\u00C0' && input.LA(1) <= '\u00D6')||(input.LA(1) >= '\u00D8' && input.LA(1) <= '\uDFFF') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					break loop8;
				}
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ID"

	// $ANTLR start "IdentifierStart"
	public final void mIdentifierStart() throws RecognitionException {
		try {
			// ConceptDiagrams.g:144:5: ( '\\u0024' | '\\u0041' .. '\\u005a' | '\\u005f' | '\\u0061' .. '\\u007a' | '\\u00a2' .. '\\u00a5' | '\\u00aa' | '\\u00b5' | '\\u00ba' | '\\u00c0' .. '\\u00d6' | '\\u00d8' .. '\\udfff' )
			// ConceptDiagrams.g:
			{
			if ( input.LA(1)=='$'||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z')||(input.LA(1) >= '\u00A2' && input.LA(1) <= '\u00A5')||input.LA(1)=='\u00AA'||input.LA(1)=='\u00B5'||input.LA(1)=='\u00BA'||(input.LA(1) >= '\u00C0' && input.LA(1) <= '\u00D6')||(input.LA(1) >= '\u00D8' && input.LA(1) <= '\uDFFF') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "IdentifierStart"

	// $ANTLR start "IdentifierPart"
	public final void mIdentifierPart() throws RecognitionException {
		try {
			// ConceptDiagrams.g:158:5: ( '\\u0000' .. '\\u0008' | '\\u000e' .. '\\u001b' | '\\u0024' | '\\u0030' .. '\\u0039' | '\\u0041' .. '\\u005a' | '\\u005f' | '\\u0061' .. '\\u007a' | '\\u007f' .. '\\u009f' | '\\u00a2' .. '\\u00a5' | '\\u00aa' | '\\u00ad' | '\\u00b5' | '\\u00ba' | '\\u00c0' .. '\\u00d6' | '\\u00d8' .. '\\udfff' )
			// ConceptDiagrams.g:
			{
			if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\b')||(input.LA(1) >= '\u000E' && input.LA(1) <= '\u001B')||input.LA(1)=='$'||(input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z')||(input.LA(1) >= '\u007F' && input.LA(1) <= '\u009F')||(input.LA(1) >= '\u00A2' && input.LA(1) <= '\u00A5')||input.LA(1)=='\u00AA'||input.LA(1)=='\u00AD'||input.LA(1)=='\u00B5'||input.LA(1)=='\u00BA'||(input.LA(1) >= '\u00C0' && input.LA(1) <= '\u00D6')||(input.LA(1) >= '\u00D8' && input.LA(1) <= '\uDFFF') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "IdentifierPart"

	@Override
	public void mTokens() throws RecognitionException {
		// ConceptDiagrams.g:1:8: ( CD | DICT | LIST | PAIR | SLIST | T__19 | T__20 | T__21 | T__22 | T__23 | COMMENT | WS | STRING | ID )
		int alt9=14;
		int LA9_0 = input.LA(1);
		if ( (LA9_0=='C') ) {
			switch ( input.LA(2) ) {
			case 'o':
				{
				int LA9_14 = input.LA(3);
				if ( (LA9_14=='n') ) {
					int LA9_16 = input.LA(4);
					if ( (LA9_16=='c') ) {
						int LA9_18 = input.LA(5);
						if ( (LA9_18=='e') ) {
							int LA9_19 = input.LA(6);
							if ( (LA9_19=='p') ) {
								int LA9_20 = input.LA(7);
								if ( (LA9_20=='t') ) {
									int LA9_21 = input.LA(8);
									if ( (LA9_21=='D') ) {
										int LA9_22 = input.LA(9);
										if ( (LA9_22=='i') ) {
											int LA9_23 = input.LA(10);
											if ( (LA9_23=='a') ) {
												int LA9_24 = input.LA(11);
												if ( (LA9_24=='g') ) {
													int LA9_25 = input.LA(12);
													if ( (LA9_25=='r') ) {
														int LA9_26 = input.LA(13);
														if ( (LA9_26=='a') ) {
															int LA9_27 = input.LA(14);
															if ( (LA9_27=='m') ) {
																int LA9_28 = input.LA(15);
																if ( ((LA9_28 >= '\u0000' && LA9_28 <= '\b')||(LA9_28 >= '\u000E' && LA9_28 <= '\u001B')||LA9_28=='$'||(LA9_28 >= '0' && LA9_28 <= '9')||(LA9_28 >= 'A' && LA9_28 <= 'Z')||LA9_28=='_'||(LA9_28 >= 'a' && LA9_28 <= 'z')||(LA9_28 >= '\u007F' && LA9_28 <= '\u009F')||(LA9_28 >= '\u00A2' && LA9_28 <= '\u00A5')||LA9_28=='\u00AA'||LA9_28=='\u00AD'||LA9_28=='\u00B5'||LA9_28=='\u00BA'||(LA9_28 >= '\u00C0' && LA9_28 <= '\u00D6')||(LA9_28 >= '\u00D8' && LA9_28 <= '\uDFFF')) ) {
																	alt9=14;
																}

																else {
																	alt9=1;
																}

															}

															else {
																alt9=14;
															}

														}

														else {
															alt9=14;
														}

													}

													else {
														alt9=14;
													}

												}

												else {
													alt9=14;
												}

											}

											else {
												alt9=14;
											}

										}

										else {
											alt9=14;
										}

									}

									else {
										alt9=14;
									}

								}

								else {
									alt9=14;
								}

							}

							else {
								alt9=14;
							}

						}

						else {
							alt9=14;
						}

					}

					else {
						alt9=14;
					}

				}

				else {
					alt9=14;
				}

				}
				break;
			case 'D':
				{
				int LA9_15 = input.LA(3);
				if ( ((LA9_15 >= '\u0000' && LA9_15 <= '\b')||(LA9_15 >= '\u000E' && LA9_15 <= '\u001B')||LA9_15=='$'||(LA9_15 >= '0' && LA9_15 <= '9')||(LA9_15 >= 'A' && LA9_15 <= 'Z')||LA9_15=='_'||(LA9_15 >= 'a' && LA9_15 <= 'z')||(LA9_15 >= '\u007F' && LA9_15 <= '\u009F')||(LA9_15 >= '\u00A2' && LA9_15 <= '\u00A5')||LA9_15=='\u00AA'||LA9_15=='\u00AD'||LA9_15=='\u00B5'||LA9_15=='\u00BA'||(LA9_15 >= '\u00C0' && LA9_15 <= '\u00D6')||(LA9_15 >= '\u00D8' && LA9_15 <= '\uDFFF')) ) {
					alt9=14;
				}

				else {
					alt9=8;
				}

				}
				break;
			default:
				alt9=14;
			}
		}
		else if ( (LA9_0=='{') ) {
			alt9=2;
		}
		else if ( (LA9_0=='[') ) {
			alt9=3;
		}
		else if ( (LA9_0=='=') ) {
			alt9=4;
		}
		else if ( (LA9_0=='(') ) {
			alt9=5;
		}
		else if ( (LA9_0==')') ) {
			alt9=6;
		}
		else if ( (LA9_0==',') ) {
			alt9=7;
		}
		else if ( (LA9_0==']') ) {
			alt9=9;
		}
		else if ( (LA9_0=='}') ) {
			alt9=10;
		}
		else if ( (LA9_0=='/') ) {
			alt9=11;
		}
		else if ( ((LA9_0 >= '\t' && LA9_0 <= '\n')||LA9_0=='\r'||LA9_0==' ') ) {
			alt9=12;
		}
		else if ( (LA9_0=='\"') ) {
			alt9=13;
		}
		else if ( (LA9_0=='$'||(LA9_0 >= 'A' && LA9_0 <= 'B')||(LA9_0 >= 'D' && LA9_0 <= 'Z')||LA9_0=='_'||(LA9_0 >= 'a' && LA9_0 <= 'z')||(LA9_0 >= '\u00A2' && LA9_0 <= '\u00A5')||LA9_0=='\u00AA'||LA9_0=='\u00B5'||LA9_0=='\u00BA'||(LA9_0 >= '\u00C0' && LA9_0 <= '\u00D6')||(LA9_0 >= '\u00D8' && LA9_0 <= '\uDFFF')) ) {
			alt9=14;
		}

		else {
			NoViableAltException nvae =
				new NoViableAltException("", 9, 0, input);
			throw nvae;
		}

		switch (alt9) {
			case 1 :
				// ConceptDiagrams.g:1:10: CD
				{
				mCD(); 

				}
				break;
			case 2 :
				// ConceptDiagrams.g:1:13: DICT
				{
				mDICT(); 

				}
				break;
			case 3 :
				// ConceptDiagrams.g:1:18: LIST
				{
				mLIST(); 

				}
				break;
			case 4 :
				// ConceptDiagrams.g:1:23: PAIR
				{
				mPAIR(); 

				}
				break;
			case 5 :
				// ConceptDiagrams.g:1:28: SLIST
				{
				mSLIST(); 

				}
				break;
			case 6 :
				// ConceptDiagrams.g:1:34: T__19
				{
				mT__19(); 

				}
				break;
			case 7 :
				// ConceptDiagrams.g:1:40: T__20
				{
				mT__20(); 

				}
				break;
			case 8 :
				// ConceptDiagrams.g:1:46: T__21
				{
				mT__21(); 

				}
				break;
			case 9 :
				// ConceptDiagrams.g:1:52: T__22
				{
				mT__22(); 

				}
				break;
			case 10 :
				// ConceptDiagrams.g:1:58: T__23
				{
				mT__23(); 

				}
				break;
			case 11 :
				// ConceptDiagrams.g:1:64: COMMENT
				{
				mCOMMENT(); 

				}
				break;
			case 12 :
				// ConceptDiagrams.g:1:72: WS
				{
				mWS(); 

				}
				break;
			case 13 :
				// ConceptDiagrams.g:1:75: STRING
				{
				mSTRING(); 

				}
				break;
			case 14 :
				// ConceptDiagrams.g:1:82: ID
				{
				mID(); 

				}
				break;

		}
	}



}
