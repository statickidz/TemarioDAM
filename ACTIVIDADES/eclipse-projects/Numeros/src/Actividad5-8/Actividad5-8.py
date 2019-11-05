# -*- coding: utf-8 -*- 
'''
Created on 18 de ene. de 2016

@author: dam2
'''

#    Operadores a nivel de bit:
bNuml =0x9 #1001
bNum2 = 0xC #1100 
print "And (&)", bNuml & bNum2 # 1000 
print "Or (|)", bNuml | bNum2 # 1101 
print "XOR (^)", bNuml ^ bNum2 # 0101 
print "Not(~)", ~bNuml
print "Desplazamiento >> de 2", bNuml >> 2 # 0010
print "Desplazamiento << de 2", bNuml << 2 # 0010 0100 tener en cuenta que son 32 o 64 bits la representacion