# Spelling Corrector

We are all familiar with spelling correctors. They help a lot of us spell words like 'necessary' and 'handkerchief'. This is code for one such implementation. It takes an input file populated with all of the words in your "dictionary", and then an input string. It then searches your dictionary for the closest matching word. 

Under the hood, it builds a trie of your dictionary, and then when you search for a word, if it doesn't exist, the program will alter your input word and see if it exists in your directory by chopping off, switched, or inserting characters. If not words in the dictionary are within 2 changes of your word, it tells you there are no similar words.
