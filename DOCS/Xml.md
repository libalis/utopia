> Xml documentation

# Public variables:

## 1. Database items:

|Variable|Type|
|--|--|
|id[]|String array|
|name[]|String array|
|amount[]|String array|
|category[]|String array|
|bestbefore[]|String array|

## 2. Database groups:

|Variable|Type|
|--|--|
|categories[]|String array|

## 3. Other variables:

|Variable|Type|Meaning|
|--|--|--|
|length|int|Amount of items
|num|int|Amount of categories

# Public functions:

## 1. Test functions:

|Function|Parameter(s)|
|--|--|--|
|printOut()|No Parameters|

## 2. Edit functions:

|Function|Parameter(s)|
|--|--|--|
|addProduct()|String name, String amount, String category|
|changeOut()|String id, String column, String row|
|*&rarr; Comment*|*1. column: "name", "amount", "category", "bestbefore"*|
||*2. row: personal input*|
|removeProduct()|String id|

## 3. Mandatory function to execute when application shuts down:

|Function|Parameter(s)|
|--|--|--|
|overWrite()|No parameters|

# Excursus: Direct variable change:

Example:

    try {
            amount[0] = String.valueOf(Integer.parseInt(amount[0]) - 1);
        } catch(Exception exception) {}

# License

	MIT License

	Copyright (c) 2020 Robert Kagan

	Permission is hereby granted, free of charge, to any person obtaining a copy
	of this software and associated documentation files (the "Software"), to deal
	in the Software without restriction, including without limitation the rights
	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	copies of the Software, and to permit persons to whom the Software is
	furnished to do so, subject to the following conditions:
	
	The above copyright notice and this permission notice shall be included in all
	copies or substantial portions of the Software.

	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
	SOFTWARE.
