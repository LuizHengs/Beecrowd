#include <stdio.h>
 
int main() {
 
    int c1, c2, q1, q2;
    float v1, v2, total;

    scanf("%d %d %f %d %d %f", &c1, &q1, &v1, &c2, &q2, &v2);

    total = q1 * v1 + q2 * v2;

    printf("VALOR A PAGAR: R$%.2f\n", total);
 
    return 0;
}