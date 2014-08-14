#
# Generated Makefile - do not edit!
#
# Edit the Makefile in the project folder instead (../Makefile). Each target
# has a -pre and a -post target defined where you can add customized code.
#
# This makefile implements configuration specific macros and targets.


# Environment
MKDIR=mkdir
CP=cp
GREP=grep
NM=nm
CCADMIN=CCadmin
RANLIB=ranlib
CC=gcc
CCC=g++
CXX=g++
FC=gfortran
AS=as

# Macros
CND_PLATFORM=GNU-Linux-x86
CND_DLIB_EXT=so
CND_CONF=Debug
CND_DISTDIR=dist
CND_BUILDDIR=build

# Include project Makefile
include Makefile

# Object Directory
OBJECTDIR=${CND_BUILDDIR}/${CND_CONF}/${CND_PLATFORM}

# Object Files
OBJECTFILES= \
	${OBJECTDIR}/c_code/ACELP_CO.o \
	${OBJECTDIR}/c_code/BASIC_OP.o \
	${OBJECTDIR}/c_code/BITS.o \
	${OBJECTDIR}/c_code/CODER.o \
	${OBJECTDIR}/c_code/COD_LD8K.o \
	${OBJECTDIR}/c_code/DECODER.o \
	${OBJECTDIR}/c_code/DEC_GAIN.o \
	${OBJECTDIR}/c_code/DEC_LAG3.o \
	${OBJECTDIR}/c_code/DEC_LD8K.o \
	${OBJECTDIR}/c_code/DE_ACELP.o \
	${OBJECTDIR}/c_code/DSPFUNC.o \
	${OBJECTDIR}/c_code/FILTER.o \
	${OBJECTDIR}/c_code/GAINPRED.o \
	${OBJECTDIR}/c_code/LPC.o \
	${OBJECTDIR}/c_code/LPCFUNC.o \
	${OBJECTDIR}/c_code/LSPDEC.o \
	${OBJECTDIR}/c_code/LSPGETQ.o \
	${OBJECTDIR}/c_code/OPER_32B.o \
	${OBJECTDIR}/c_code/PITCH.o \
	${OBJECTDIR}/c_code/POST_PRO.o \
	${OBJECTDIR}/c_code/PRED_LT3.o \
	${OBJECTDIR}/c_code/PRE_PROC.o \
	${OBJECTDIR}/c_code/PST.o \
	${OBJECTDIR}/c_code/PWF.o \
	${OBJECTDIR}/c_code/P_PARITY.o \
	${OBJECTDIR}/c_code/QUA_GAIN.o \
	${OBJECTDIR}/c_code/QUA_LSP.o \
	${OBJECTDIR}/c_code/TAB_LD8K.o \
	${OBJECTDIR}/c_code/UTIL.o \
	${OBJECTDIR}/main.o


# C Compiler Flags
CFLAGS=

# CC Compiler Flags
CCFLAGS=
CXXFLAGS=

# Fortran Compiler Flags
FFLAGS=

# Assembler Flags
ASFLAGS=

# Link Libraries and Options
LDLIBSOPTIONS=

# Build Targets
.build-conf: ${BUILD_SUBPROJECTS}
	"${MAKE}"  -f nbproject/Makefile-${CND_CONF}.mk ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/g729iut

${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/g729iut: ${OBJECTFILES}
	${MKDIR} -p ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}
	${LINK.cc} -o ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/g729iut ${OBJECTFILES} ${LDLIBSOPTIONS}

${OBJECTDIR}/c_code/ACELP_CO.o: c_code/ACELP_CO.C 
	${MKDIR} -p ${OBJECTDIR}/c_code
	${RM} "$@.d"
	$(COMPILE.cc) -g -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/c_code/ACELP_CO.o c_code/ACELP_CO.C

${OBJECTDIR}/c_code/BASIC_OP.o: c_code/BASIC_OP.C 
	${MKDIR} -p ${OBJECTDIR}/c_code
	${RM} "$@.d"
	$(COMPILE.cc) -g -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/c_code/BASIC_OP.o c_code/BASIC_OP.C

${OBJECTDIR}/c_code/BITS.o: c_code/BITS.C 
	${MKDIR} -p ${OBJECTDIR}/c_code
	${RM} "$@.d"
	$(COMPILE.cc) -g -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/c_code/BITS.o c_code/BITS.C

${OBJECTDIR}/c_code/CODER.o: c_code/CODER.C 
	${MKDIR} -p ${OBJECTDIR}/c_code
	${RM} "$@.d"
	$(COMPILE.cc) -g -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/c_code/CODER.o c_code/CODER.C

${OBJECTDIR}/c_code/COD_LD8K.o: c_code/COD_LD8K.C 
	${MKDIR} -p ${OBJECTDIR}/c_code
	${RM} "$@.d"
	$(COMPILE.cc) -g -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/c_code/COD_LD8K.o c_code/COD_LD8K.C

${OBJECTDIR}/c_code/DECODER.o: c_code/DECODER.C 
	${MKDIR} -p ${OBJECTDIR}/c_code
	${RM} "$@.d"
	$(COMPILE.cc) -g -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/c_code/DECODER.o c_code/DECODER.C

${OBJECTDIR}/c_code/DEC_GAIN.o: c_code/DEC_GAIN.C 
	${MKDIR} -p ${OBJECTDIR}/c_code
	${RM} "$@.d"
	$(COMPILE.cc) -g -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/c_code/DEC_GAIN.o c_code/DEC_GAIN.C

${OBJECTDIR}/c_code/DEC_LAG3.o: c_code/DEC_LAG3.C 
	${MKDIR} -p ${OBJECTDIR}/c_code
	${RM} "$@.d"
	$(COMPILE.cc) -g -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/c_code/DEC_LAG3.o c_code/DEC_LAG3.C

${OBJECTDIR}/c_code/DEC_LD8K.o: c_code/DEC_LD8K.C 
	${MKDIR} -p ${OBJECTDIR}/c_code
	${RM} "$@.d"
	$(COMPILE.cc) -g -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/c_code/DEC_LD8K.o c_code/DEC_LD8K.C

${OBJECTDIR}/c_code/DE_ACELP.o: c_code/DE_ACELP.C 
	${MKDIR} -p ${OBJECTDIR}/c_code
	${RM} "$@.d"
	$(COMPILE.cc) -g -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/c_code/DE_ACELP.o c_code/DE_ACELP.C

${OBJECTDIR}/c_code/DSPFUNC.o: c_code/DSPFUNC.C 
	${MKDIR} -p ${OBJECTDIR}/c_code
	${RM} "$@.d"
	$(COMPILE.cc) -g -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/c_code/DSPFUNC.o c_code/DSPFUNC.C

${OBJECTDIR}/c_code/FILTER.o: c_code/FILTER.C 
	${MKDIR} -p ${OBJECTDIR}/c_code
	${RM} "$@.d"
	$(COMPILE.cc) -g -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/c_code/FILTER.o c_code/FILTER.C

${OBJECTDIR}/c_code/GAINPRED.o: c_code/GAINPRED.C 
	${MKDIR} -p ${OBJECTDIR}/c_code
	${RM} "$@.d"
	$(COMPILE.cc) -g -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/c_code/GAINPRED.o c_code/GAINPRED.C

${OBJECTDIR}/c_code/LPC.o: c_code/LPC.C 
	${MKDIR} -p ${OBJECTDIR}/c_code
	${RM} "$@.d"
	$(COMPILE.cc) -g -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/c_code/LPC.o c_code/LPC.C

${OBJECTDIR}/c_code/LPCFUNC.o: c_code/LPCFUNC.C 
	${MKDIR} -p ${OBJECTDIR}/c_code
	${RM} "$@.d"
	$(COMPILE.cc) -g -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/c_code/LPCFUNC.o c_code/LPCFUNC.C

${OBJECTDIR}/c_code/LSPDEC.o: c_code/LSPDEC.C 
	${MKDIR} -p ${OBJECTDIR}/c_code
	${RM} "$@.d"
	$(COMPILE.cc) -g -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/c_code/LSPDEC.o c_code/LSPDEC.C

${OBJECTDIR}/c_code/LSPGETQ.o: c_code/LSPGETQ.C 
	${MKDIR} -p ${OBJECTDIR}/c_code
	${RM} "$@.d"
	$(COMPILE.cc) -g -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/c_code/LSPGETQ.o c_code/LSPGETQ.C

${OBJECTDIR}/c_code/OPER_32B.o: c_code/OPER_32B.C 
	${MKDIR} -p ${OBJECTDIR}/c_code
	${RM} "$@.d"
	$(COMPILE.cc) -g -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/c_code/OPER_32B.o c_code/OPER_32B.C

${OBJECTDIR}/c_code/PITCH.o: c_code/PITCH.C 
	${MKDIR} -p ${OBJECTDIR}/c_code
	${RM} "$@.d"
	$(COMPILE.cc) -g -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/c_code/PITCH.o c_code/PITCH.C

${OBJECTDIR}/c_code/POST_PRO.o: c_code/POST_PRO.C 
	${MKDIR} -p ${OBJECTDIR}/c_code
	${RM} "$@.d"
	$(COMPILE.cc) -g -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/c_code/POST_PRO.o c_code/POST_PRO.C

${OBJECTDIR}/c_code/PRED_LT3.o: c_code/PRED_LT3.C 
	${MKDIR} -p ${OBJECTDIR}/c_code
	${RM} "$@.d"
	$(COMPILE.cc) -g -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/c_code/PRED_LT3.o c_code/PRED_LT3.C

${OBJECTDIR}/c_code/PRE_PROC.o: c_code/PRE_PROC.C 
	${MKDIR} -p ${OBJECTDIR}/c_code
	${RM} "$@.d"
	$(COMPILE.cc) -g -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/c_code/PRE_PROC.o c_code/PRE_PROC.C

${OBJECTDIR}/c_code/PST.o: c_code/PST.C 
	${MKDIR} -p ${OBJECTDIR}/c_code
	${RM} "$@.d"
	$(COMPILE.cc) -g -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/c_code/PST.o c_code/PST.C

${OBJECTDIR}/c_code/PWF.o: c_code/PWF.C 
	${MKDIR} -p ${OBJECTDIR}/c_code
	${RM} "$@.d"
	$(COMPILE.cc) -g -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/c_code/PWF.o c_code/PWF.C

${OBJECTDIR}/c_code/P_PARITY.o: c_code/P_PARITY.C 
	${MKDIR} -p ${OBJECTDIR}/c_code
	${RM} "$@.d"
	$(COMPILE.cc) -g -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/c_code/P_PARITY.o c_code/P_PARITY.C

${OBJECTDIR}/c_code/QUA_GAIN.o: c_code/QUA_GAIN.C 
	${MKDIR} -p ${OBJECTDIR}/c_code
	${RM} "$@.d"
	$(COMPILE.cc) -g -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/c_code/QUA_GAIN.o c_code/QUA_GAIN.C

${OBJECTDIR}/c_code/QUA_LSP.o: c_code/QUA_LSP.C 
	${MKDIR} -p ${OBJECTDIR}/c_code
	${RM} "$@.d"
	$(COMPILE.cc) -g -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/c_code/QUA_LSP.o c_code/QUA_LSP.C

${OBJECTDIR}/c_code/TAB_LD8K.o: c_code/TAB_LD8K.C 
	${MKDIR} -p ${OBJECTDIR}/c_code
	${RM} "$@.d"
	$(COMPILE.cc) -g -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/c_code/TAB_LD8K.o c_code/TAB_LD8K.C

${OBJECTDIR}/c_code/UTIL.o: c_code/UTIL.C 
	${MKDIR} -p ${OBJECTDIR}/c_code
	${RM} "$@.d"
	$(COMPILE.cc) -g -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/c_code/UTIL.o c_code/UTIL.C

${OBJECTDIR}/main.o: main.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.cc) -g -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/main.o main.cpp

# Subprojects
.build-subprojects:

# Clean Targets
.clean-conf: ${CLEAN_SUBPROJECTS}
	${RM} -r ${CND_BUILDDIR}/${CND_CONF}
	${RM} ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/g729iut

# Subprojects
.clean-subprojects:

# Enable dependency checking
.dep.inc: .depcheck-impl

include .dep.inc
