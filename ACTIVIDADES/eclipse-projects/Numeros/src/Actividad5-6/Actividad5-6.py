# -*- coding: utf-8 -*- 
'''
Created on 18 de ene. de 2016

@author: dam2
'''

from decimal import Decimal, getcontext

fNuml = 3.34 # float o reales usa doble precisión
dNum1 = Decimal(fNuml)  # La mayot pecision posible
print "real y decimal", fNuml, dNum1
getcontext().prec = 6
print "Precision 6:", Decimal(1) / Decimal(7)
getcontext().prec = 28
print "Precision 28:", Decimal(1) / Decimal(7)