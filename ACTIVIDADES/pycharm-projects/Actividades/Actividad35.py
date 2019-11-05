# -*- coding: utf-8 -*

"""
Actividad 5.35
Convierte la lista a=[1,[2,[3,4]],5] en [1,[2,[3,4],[6,7]], 5].
"""

a = [1,[2,[3,4]],5]
aModificar = a[1]
aModificar.append([6,7])

print a