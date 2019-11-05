# -*- coding: utf-8 -*-
import sqlite3 as dbapi

bbdd = dbapi.connect(":memory:")  # Guardar en memoria en vez de en disco

c = bbdd.cursor()

c.execute("""create table if not exists em(dni text,nombre text, departamento text)""")
c.execute("""insert into em values ('a1','b1','c1')""")
c.execute("""insert into em values ('a2','b2','c2')""")
c.execute("""insert into em values ('a3','b3','c3')""")
bbdd.commit()

c.execute("""select * from em""")
for t in c.fetchall():
    print t

c.execute("""select * from em""")
t = c.fetchone()
while (t):
    print t
    t = c.fetchone()
