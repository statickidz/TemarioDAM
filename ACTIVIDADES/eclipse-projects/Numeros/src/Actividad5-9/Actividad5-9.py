# -*- coding: utf-8 -*- 
'''
Created on 18 de ene. de 2016

@author: dam2
'''

from decimal import Decimal
fNuml = 3.34  # float o reales usa doble precision
INuml = 3L  # Entero largo como long de C
dNuml = Decimal(fNuml) # La mayor precision posible 
print"    Funciones basicas    "
print dNuml .copy_abs() 
print dNuml .is_infinite() 
print dNuml.log10() 
print dNuml.sqrt() 
print dNuml.max(INuml)
print fNuml.hex() # representacion hexadecimal
print INuml.bit_length()  # bits necesarios para representar el valor

