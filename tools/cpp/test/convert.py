f = open("commands1.txt", 'r')
f2 = open("commands.txt", 'w')

for l in f:
	if l.startswith('E') or l.startswith('M'):
		print>>f2, l[0],
	else:
		print>>f2, l[:-1]
