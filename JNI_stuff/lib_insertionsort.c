#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <jni.h>
#include "InsertionSort.h"

int count_file_lines(const char* filename);
int load_file_to_array(int * int_array,int size,const char* fileName);
int save_array_to_file(int * int_array,int size,const char* fileName);
void sort(int * int_array,int size);
int compute_hazard(double failureProbablity);

int memory_accesses;

JNIEXPORT jint JNICALL Java_InsertionSort_insertionSortMethod
(JNIEnv *env,jobject callingObject, jstring inputFileNameJava, jstring outputFileNameJava, jdouble failureProbablityJava) {

  memory_accesses=0;

  double failureProbablity = failureProbablityJava;
  printf("failureProbablity: %f\n",failureProbablity );

  const char * inputFileName;
  inputFileName=(*env)->GetStringUTFChars(env, inputFileNameJava, NULL);

  const char * outputFileName;
  outputFileName=(*env)->GetStringUTFChars(env, outputFileNameJava, NULL);

  // http://stackoverflow.com/questions/5471510/how-much-overhead-do-realloc-calls-introduce/16819375
  // so I will loop once to find storage requirements
  int size=count_file_lines(inputFileName);
  if(size==-1) return -1;

  int int_array[size];

  if(load_file_to_array(int_array,size,inputFileName)==-1) return -1;
  sort(int_array,size);
  if(save_array_to_file(int_array,size,outputFileName)==-1) return -1;

  printf("size: %d\n",size);


  int result=compute_hazard(failureProbablity);

  return result;
}

// http://stackoverflow.com/questions/6218399/how-to-generate-a-random-number-between-0-and-1
int compute_hazard(double failureProbablity){
  time_t t;
  // seeding random
  // https://www.tutorialspoint.com/c_standard_library/c_function_srand.htm
  srand((unsigned) time(&t));
  double HAZARD=memory_accesses*failureProbablity;
  double random_number=(double)rand() / (double)RAND_MAX ;
  printf("h:%f\n",HAZARD );
  printf("r:%f\n",random_number );
  printf("0.5+h:%f\n",HAZARD+0.5);
  printf("%d\n",memory_accesses );
  if((0.5<random_number)&&random_number<(0.5+HAZARD)) return -1;
  return 1;
}
// http://stackoverflow.com/questions/12733105/c-function-that-counts-lines-in-file
// helper function to find storage requirements, wont count access cause it isn't
// part of the sorting routine
int count_file_lines(const char* fileName){
  FILE * file_pointer;
  file_pointer=fopen(fileName,"r");
  if(file_pointer==NULL) return -1;
  int line_count=0;
  int character_value;
  while(!feof(file_pointer)){
    character_value = fgetc(file_pointer);
    if(character_value=='\n') line_count++;
  }
  return line_count;
}
// http://stackoverflow.com/questions/3501338/c-read-file-line-by-line
int load_file_to_array(int * int_array,int size,const char* fileName){
  FILE *file_pointer = fopen(fileName,"r");
  if(file_pointer==NULL) return -1;

  // 100 characters is far larger than any int value
  const size_t line_size=100;
  char * line=malloc(line_size);
  int counter=0;
  int value;
  char * next_char_pointer;
  while(fgets(line,line_size,file_pointer) != NULL){
    // very nifty
    // http://stackoverflow.com/questions/2693776/removing-trailing-newline-character-from-fgets-input
    // Tim Čas
    line[strcspn(line,"\n")]=0;
    value=strtol(line,&next_char_pointer,10);
    int_array[counter]=value;
    // printf("%d\n",value );
    counter++;
  }

  return size;
}

int save_array_to_file(int * int_array,int size,const char* fileName){
  FILE *file_pointer = fopen(fileName,"w");
  if(file_pointer==NULL) return -1;
  int i;
  for(i=0;i<size;i++){
    fprintf(file_pointer, "%d\n",int_array[i] );
  }

  return size;
}
// looked at wikipedia pseudocode
void sort(int * int_array,int size){
  memory_accesses=0;
  int i; // counter for whole array
  int j; // sub counter for partially sorted sub array
  int current_comparison_value;
  // last conditional check and initial setup
  for(i=0;i<size;i++){
    memory_accesses=memory_accesses+1;
    current_comparison_value=int_array[i];
    j=i-1;
    // last conditional
    memory_accesses=memory_accesses+1;
    while (j>=0 && int_array[j]>current_comparison_value) {
      memory_accesses=memory_accesses+2;
      int_array[j+1]=int_array[j];
      j--;
    }
    memory_accesses=memory_accesses+1;
    int_array[j+1]=current_comparison_value;
    // for i counter
  }
}
