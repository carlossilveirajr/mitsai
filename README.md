Miner of Temporal Series Image (MiTSAI) is a data mining algorithm for
the association-rule extraction from spatiotemporal images.

MiTSAI (original name Mad Hatter) is a Ph.D. project not designated to commercial use.
All improvement should be sent back and must be freely available.

Carlos Roberto Silveira Junior
Mad Hatter creator at January 28th, 2015.


# Extract, Transform, and Load

                              | DISCRETIZATOR     |
                                ||
                              | FEATURE EXTRACTOR |
                                ||
Record File  -| EXTRACT   |- -| TRANSFORM  |- -| LOAD |- [ Target ]
              |           |   |<<abstract>>|   |      |  [        ]
                    ||
              | SOLAR INSTANCE |
              | EXTRACT        |

The EXTRACT must read the Record File that is supported only JSon format,
but it can be extended during EXTRACT extension.
EXTRACT sends each record to TRANSFORM which extract the image feature
if it has, using the FEATURE EXTRACTOR (support one is SURF).
The feature can be discretized by DISCRETIZATOR (supported algorithm, Omega).
After transformed the records go to LOAD that will persist it in the Target
(supported one, MongoDB).

ETL property allows to set the FEATURE EXTRACTOR (when required),
DISCRETIZATOR algorithm (when required), and Target (type of Database and its
configuration, or if it is a database).

# Running

Database must be running. For it we use a docker container

docker run -d -p 27017:27017 -v /home/junior/solar/experiment-miner/data:/data/db mongo